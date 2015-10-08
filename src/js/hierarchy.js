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
hierarchy.graphdata = {};
hierarchy.url = 'localhost:2392';

hierarchy.pointOnClick = function(data) {



};

hierarchy.updateGraphs = function() {

    var childName = hierarchy.selectedNode.id;

    var path = hierarchy.path.join("|");

    var data = {};

    data.path = path;
    data.name = childName;
    var fromTime = document.getElementById("fromTime").value;
    var toTime = document.getElementById("toTime").value;

    if(fromTime != undefined && fromTime.length > 0) {
       data.fromTime = new Date(fromTime).getTime();
    } else {
        data.fromTime = -1;
    }

    if(toTime != undefined && toTime.length > 0) {
        data.toTime = new Date(toTime).getTime();
    } else {
        data.toTime = -1;
    }
    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/getChart/1",
        data: JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(data) {
        console.log(data);

        removeNulls(data.graph1);
        function removeNulls(obj){
            var isArray = obj instanceof Array;
            for (var k in obj){
                if (obj[k]===null) isArray ? obj.splice(k,1) : delete obj[k];
                else if (typeof obj[k]=="object") removeNulls(obj[k]);
            }
        }
        hierarchy.graphdata = data;



        $.each(data, logMapElements);

        function logMapElements( index, value) {
            data[index].plotOptions.series ={};
            data[index].plotOptions.series.cursor = "pointer";
            data[index].plotOptions.series.point ={};
            data[index].plotOptions.series.point.events ={};
            data[index].plotOptions.series.point.events['click'] =  function(e) {
                console.log(this);
                document.getElementById("updateValues").hidden = false;

                document.getElementById("updateValueName").value = this.series.name;
                document.getElementById("updateX").value = new Date(parseInt(this.x));
                document.getElementById("updateY").value = this.y;
            }
        }

        $('#hichart_container').highcharts(data.graph1);

        $('#hichart_picontainer').highcharts(data.graph2);

        console.log(JSON.stringify(data));

    }

};
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
        hierarchy.json = response[document.getElementById("trackitGroups").value];
        st.loadJSON(hierarchy.json);// or $(this).val()
        //compute node positions and layout
        st.compute();
        //optional: make a translation of the tree
        st.geom.translate(new $jit.Complex(-200, 0), "current");
        //emulate a click on the root node.
        st.onClick(hierarchy.selectedNode.id);
        //st.refresh();
    }
}

function generateAggChart() {
    var aggData = {};
    var elementData = {};
    var aggType = document.getElementById("main_aggregations_aggregation_type").value;
    var graphType = document.getElementById("main_aggregations_graph_type").value;
    var interval = document.getElementById("main_aggregations_interval_count").value;
    var intervalType = document.getElementById("main_aggregations_interval_type").value;
    var fromTime = document.getElementById("main_aggregations_fromTime").value;
    var toTime = document.getElementById("main_aggregations_toTime").value;

    if(fromTime != undefined && fromTime.length > 0) {
        aggData.fromTime = new Date(childTime).getTime();
    } else {
        aggData.fromTime = -1;
    }

    if(toTime != undefined && toTime.length > 0) {
        aggData.toTime = new Date(toTime).getTime();
    } else {
        aggData.toTime = -1;
    }

    aggData.aggreationType = aggType;
    aggData.interval = interval;
    aggData.intervalType = intervalType;
    aggData.groupBy = "";
    aggData.flags = {};
    aggData.flags.includeChildren = true;


    elementData.name = hierarchy.selectedNode.id;

    elementData.path = hierarchy.path.join("|");
    elementData.graphType = graphType;
    elementData.values = {};
    elementData.values.count = "int";

    var data = {};
    data.aggregation = aggData;
    data.element = elementData;

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/getAggChart/1",
        data:  JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(response) {

        removeNulls(response);
        console.log(response);

        function removeNulls(obj){
            var isArray = obj instanceof Array;
            for (var k in obj){
                if (obj[k]===null) isArray ? obj.splice(k,1) : delete obj[k];
                else if (typeof obj[k]=="object") removeNulls(obj[k]);
            }
        }
        $('#main_aggregations_graph').highcharts(response);
    }

}
function addValueToNode() {
    var childValue = document.getElementById("childValue").value;
    var childNotes = document.getElementById("childNotes").value;
    var childName = hierarchy.selectedNode.id;

    if(childValue == undefined || childValue.length == 0) {
        alert("Please enter a valid value");
    }

    var path = hierarchy.path.join("|");

    var data = {};

    data.path = path;
    data.name = childName;
    data.value = childValue;
    data.notes = childNotes;

    var childTime = document.getElementById("childTime").value;

    if(childTime != undefined && childTime.length > 0) {
        data.time = new Date(childTime).getTime();
    } else {
        data.time = -1;
    }

    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "http://" + hierarchy.url + "//trackit/addValue/1",
        data:  JSON.stringify(data),
        success: reloadGraph,
        dataType: 'json'
    });

    function reloadGraph(response) {
        alert("Successfully added data")
        document.getElementById("childValue").value = "";
        document.getElementById("childNotes").value = "";
        st.onClick(hierarchy.selectedNode.id);
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
