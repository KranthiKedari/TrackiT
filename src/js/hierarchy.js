var labelType, useGradients, nativeTextSupport, animate;

(function() {
    var ua = navigator.userAgent,
        iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
        typeOfCanvas = typeof HTMLCanvasElement,
        nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
        textSupport = nativeCanvasSupport
            && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
    //I'm setting this based on the fact that ExCanvas provides text support for IE
    //and that as of today iPhone/iPad current text support is lame
    labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
    nativeTextSupport = labelType == 'Native';
    useGradients = nativeCanvasSupport;
    animate = !(iStuff || !nativeCanvasSupport);
})();
hierarchy = {};
st = "";
hierarchy.selectedNode = "";
hierarchy.json = "";
hierarchy.path = [];
hierarchy.url = 'localhost:2392';
hierarchy.updateGraphs = function() {

    var childName = hierarchy.selectedNode.id;

    var path = hierarchy.path.join("|");

    var data = {};

    data.path = path;
    data.name = childName;

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/getData/1",
        data: JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(data) {
        console.log(data);
        data.data.graph1.forEach(logMapElements)
        data.data.graph2.forEach(logMapElements1)
        function logMapElements(element, index, array) {
            array[index].type = 'area';
        }

        function logMapElements1(element, index, array) {
            array[index].type = 'pie';
        }

        $('#hichart_container').highcharts({
            chart: {
                zoomType: 'x'
            },
            title: {
                text: data.path
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
            },
            xAxis: {
                type: 'datetime'
            },
            yAxis : [{ // left y axis
                title: {
                    text: data.unit
                },
                labels: {
                    align: 'left',
                    x: '0',
                    y: 0,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }, { // right y axis
                linkedTo: 0,
                gridLineWidth: 0,
                opposite: true,
                title: {
                    text: data.unit
                },
                labels: {
                    align: 'right',
                    x: 0,
                    y: 0,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }],
            legend: {
                align: 'left',
                verticalAlign: 'bottom',
                y: 20,
                floating: true,
                borderWidth: 0
            },

            tooltip: {
                shared: true,
                crosshairs: true
            },
          /*  plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: {
                        radius: 2
                    },
                    lineWidth: 1,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    threshold: null
                }
            },*/
            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function (e) {
                                console.log(e);
                                console.log(this.x);
                                console.log(this.y);
                                console.log(this);
                            }
                        }
                    },
                    marker: {
                        lineWidth: 1
                    },
                    lineWidth: 1,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    threshold: null
                }
            },

            series: data.data.graph1
        });


        $('#hichart_picontainer').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            title: {
                text: data.path + "[ TOTAL = " + data.fields.total + "]"
            },

            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        format: '{point.name}'
                    }
                }
            },
            series: data.data.graph2
        });

    }

}
var Log = {
    elem: false,
    write: function(text){
        if (!this.elem)
            this.elem = document.getElementById('log');
        this.elem.innerHTML = text;
        this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
    }
};
function displayAddGroup() {
    document.getElementById("addGroupDiv").hidden = false;

}

function addGroup() {
    var groupName = document.getElementById("groupName").value;
    var isHidden = document.getElementById("hiddenField").value;

    var node = {};
    node.id = groupName;
    node.name = groupName;
    node.data = {};
    node.children = {};

    if(isHidden) {
        node.data.hidden = "1";
    }

    var data = {};

    data.path = "";
    data.node = node;
    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/addGroup/1",
        data:  JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(response) {

        document.getElementById("addGroupDiv").hidden = true;

        $('#trackitGroups')
            .find('option')
            .remove()
            .end()
        ;

        var groups =Object.keys(response);
        groupElem = document.getElementById("trackitGroups");

        groups.forEach(function(entry) {
            var optn = document.createElement("OPTION");
            optn.text = entry;
            if(entry == groupName) {
                optn.selected = true;
            }
            optn.value = entry;
            groupElem.options.add(optn);
        });
        hierarchy.json = response[groupName];
        st.loadJSON(hierarchy.json);// or $(this).val()
        //compute node positions and layout
        st.compute();
        //optional: make a translation of the tree
        st.geom.translate(new $jit.Complex(-200, 0), "current");
        //emulate a click on the root node.
        st.onClick(st.root);
        //st.refresh();
    }
}
function addChildToTree() {
    var childName = document.getElementById("childName").value;

    var path = hierarchy.path.join("|");
    var node = {};
    node.id = childName;
    node.name = childName;
    node.data = {};
    node.children = {};
    var isHidden = document.getElementById("hiddenField").value;

    if(isHidden) {
        node.data.hidden = "1";
    }

    var data = {};

    data.path = path + "|" + hierarchy.selectedNode.id;
    data.node = node;
    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/addNode/1",
        data:  JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(response) {
        var groups =Object.keys(response);
        groupElem = document.getElementById("trackitGroups");

        groups.forEach(function(entry) {
            var optn = document.createElement("OPTION");
            optn.text = entry;
            optn.value = entry;
            groupElem.options.add(optn);
        });
        hierarchy.json = response[document.getElementById("trackitGroups").value];
        st.loadJSON(hierarchy.json);// or $(this).val()
        //compute node positions and layout
        st.compute();
        //optional: make a translation of the tree
        st.geom.translate(new $jit.Complex(-200, 0), "current");
        //emulate a click on the root node.
        st.onClick(st.root);
        //st.refresh();
    }
}

function addValueToNode() {
    var childValue = document.getElementById("childValue").value;
    var childName = hierarchy.selectedNode.id;

    if(childValue == undefined || childValue.length == 0) {
        alert("Please enter a valid value");
    }

    var path = hierarchy.path.join("|");

    var data = {};

    data.path = path;
    data.name = childName;
    data.value = childValue;
    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/addValue/1",
        data:  JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(response) {

        console.log(response);
    }
}

function getPath(node) {
    var root = node;
    var path = node.id;

    if(node.anySubnode("exist")) {
        node.eachSubnode(getSelectedNode);
    }
}

function getSelectedNode(node) {
    alert(node.id);
}

function loadGraph() {

    if(st == "") {
        st = init();
    }
    $.get("http://" + hierarchy.url + "/trackit/jit/get//1", getJson)

    function getJson(data, response) {
        var groups =Object.keys(data);
        groupElem = document.getElementById("trackitGroups");

        groups.forEach(function(entry) {
            var optn = document.createElement("OPTION");
            optn.text = entry;
            optn.value = entry;
            groupElem.options.add(optn);
        });
        update(groups[0]);
        $('#trackitGroups').on('change', function() {
            update(this.value);
            //end
        });

        function update(value) {
            hierarchy.json = data[value];
            st.loadJSON(data[value]);// or $(this).val()
            //compute node positions and layout
            st.compute();
            //optional: make a translation of the tree
            st.geom.translate(new $jit.Complex(-200, 0), "current");
            //emulate a click on the root node.
            st.onClick(st.root);
        }

    }

}
function init(){
    //init dat
    var json = json;
    //end
    //init Spacetree

    //Create a new ST instance
     st = new $jit.ST({
        //id of viz container element
        injectInto: 'infovis',
        //set duration for the animation
        duration: 800,
        //set animation transition type
        transition: $jit.Trans.Quart.easeInOut,
        //set distance between node and its children
        levelDistance: 50,
        //enable panning
        Navigation: {
            enable:true,
            panning:true
        },
        //set node and edge styles
        //set overridable=true for styling individual
        //nodes or edges
        Node: {
            height: 20,
            width: 60,
            type: 'rectangle',
            color: '#aaa',
            overridable: true
        },

        Edge: {
            type: 'bezier',
            overridable: true
        },

        onBeforeCompute: function(node){
            hierarchy.path = [];
            Log.write("loading " + node.name);
            hierarchy.selectedNode = node;
        },

        onAfterCompute: function(){
            hierarchy.updateGraphs();
            Log.write("done");

        },

        //This method is called on DOM label creation.
        //Use this method to add event handlers and styles to
        //your node.
        onCreateLabel: function(label, node){
            label.id = node.id;
            label.innerHTML = node.name;
            label.onclick = function(){
                if(normal.checked) {
                    st.onClick(node.id);
                    hierarchy.selectedNode = node;
                } else {
                    st.setRoot(node.id, 'animate');
                }
            };
            //set label styles
            var style = label.style;
            style.width = 60 + 'px';
            style.height = 17 + 'px';
            style.cursor = 'pointer';
            style.color = '#333';
            style.fontSize = '0.8em';
            style.textAlign= 'center';
            style.paddingTop = '3px';
        },


        //This method is called right before plotting
        //a node. It's useful for changing an individual node
        //style properties before plotting it.
        //The data properties prefixed with a dollar
        //sign will override the global node style properties.
        onBeforePlotNode: function(node){
            //add some color to the nodes in the path between the
            //root node and the selected node.
            if (node.selected) {
                node.data.$color = "#ff7";
            }
            else {
                delete node.data.$color;
                //if the node belongs to the last plotted level
                if(!node.anySubnode("exist")) {
                    //count children number
                    var count = 0;
                    node.eachSubnode(function(n) {
                        count++; });
                    //assign a node color based on
                    //how many children it has
                    node.data.$color = ['#aaa', '#baa', '#caa', '#daa', '#eaa', '#faa'][count];
                }
            }
        },

        //This method is called right before plotting
        //an edge. It's useful for changing an individual edge
        //style properties before plotting it.
        //Edge data proprties prefixed with a dollar sign will
        //override the Edge global style properties.
        onBeforePlotLine: function(adj){
            if (adj.nodeFrom.selected && adj.nodeTo.selected) {
                adj.data.$color = "#eed";
                adj.data.$lineWidth = 3;
                if(hierarchy.path.indexOf(adj.nodeFrom.id) == -1) {
                    hierarchy.path.push(adj.nodeFrom.id);
                }
            }
            else {
                delete adj.data.$color;
                delete adj.data.$lineWidth;
            }
        }
    });



    //Add event handlers to switch spacetree orientation.
    var top = $jit.id('r-top'),
        left = $jit.id('r-left'),
        bottom = $jit.id('r-bottom'),
        right = $jit.id('r-right'),
        normal = $jit.id('s-normal');


    function changeHandler() {
        if(this.checked) {
            top.disabled = bottom.disabled = right.disabled = left.disabled = true;
            st.switchPosition(this.value, "animate", {
                onComplete: function(){
                    top.disabled = bottom.disabled = right.disabled = left.disabled = false;
                }
            });
        }
    };

    top.onchange = left.onchange = bottom.onchange = right.onchange = changeHandler;
    //end
    return st;
}
