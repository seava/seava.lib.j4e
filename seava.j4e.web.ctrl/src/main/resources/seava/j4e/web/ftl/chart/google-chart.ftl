<html>
  <head>
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
		['${xField}', '${yField}'],
		<#list dataList  as d>
	       ['${d[xField]?string("yyyy-MM-dd")}', ${d[yField]?string("0.######")}],
		  </#list>
      ]);

      var options = {
        title: '${title}'
      };

      var chart = new ${chart}(document.getElementById('chart_div'));
      chart.draw(data, options);
    }
    </script>
  </head>

  <body>  
 
    <div id="chart_div" style="width:1000; height:660"></div>
  </body>
</html>