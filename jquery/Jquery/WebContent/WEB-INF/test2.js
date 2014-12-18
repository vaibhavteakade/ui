google.load("visualization", "1", {
		packages : [ "corechart" ]
	});
	google.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = google.visualization.arrayToDataTable([
				[ 'Time', 'IOPS', 'THROUGH PUT' ], [ '5:am', 300, 400 ],
				[ '5:10 AM', 500, 600 ], [ '5:20 AM', 720, 820 ],
				[ '5:20 AM', 610, 710 ], [ '5:20 AM', 545, 645 ],
				[ '5:20 AM', 428, 528 ], [ '5:20 AM', 612, 712 ],
				[ '5:20 AM', 688, 788 ], [ '5:20 AM', 120, 220 ],
				[ '5:20 AM', 188, 288 ], [ '5:20 AM', 210, 310 ],
				[ '5:20 AM', 512, 612 ], [ '5:20 AM', 413, 513 ],
				[ '5:20 AM', 615, 715 ], [ '5:20 AM', 777, 888 ],
				[ '5:20 AM', 198, 298 ], [ '5:20 AM', 200, 300 ], ]);

		var options = {
			hAxis : {
				textPosition : 'none'
			},
			vAxis : {
				textPosition : 'none'
			},
			legend : {
				position : 'none'
			},
			chartArea : {
				'width' : '100%',
				'height' : '80%'
			},
			colors : [ 'red', 'green' ],
		};

		var chart = new google.visualization.AreaChart(document
				.getElementById('chart_div'));
		chart.draw(data, options);

		setInterval(function() {
			data.add
			data.setValue(0, 1, 40 + Math.round(60 * Math.random()));
			chart.draw(data, options);
		}, 1000);
	}
	
	$(document).ready(function() {

		$('#infoCPU').click(function() {
			$('#myModal').modal({
				show : true
			})
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			});			

			$(function() {
				$('#myTab a:last').tab('show');
			})
		});
		
		$('#infoDS').click(function() {
			$('#myModal').modal({
				show : true
			})
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			});			

			$(function() {
				$('#myTab a:last').tab('show');
			})
		});
		
		$('#infoHost').click(function() {
			$('#myModal').modal({
				show : true
			})
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			});			

			$(function() {
				$('#myTab a:last').tab('show');
			})
		});
		
		$('#infoVM').click(function() {
			$('#myModal').modal({
				show : true
			})
			$('#myTab a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			});			

			$(function() {
				$('#myTab a:last').tab('show');
			})
		});

	});	
