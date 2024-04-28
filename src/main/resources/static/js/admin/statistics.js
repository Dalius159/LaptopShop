import {Chart} from "../chart.min";

window.onload = function () {
    fetch("http://localhost:8080/laptopshop/api/order/report")
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response error')
            }
            return response.json();
        })
        .then(data => {
            let label = [];
            let dataForDataSets = [];

            for (let i = 0; i < data.length; i++) {
                label.push(data[i][0] + "/" + data[i][1]);
                dataForDataSets.push(data[i][2] / 1000000);
            }

            const canvas = document.getElementById('myChart');
            const chartData = {
                labels: label,
                datasets: [{
                    label: "Total value",
                    backgroundColor: "#0000ff",
                    borderColor: "#0000ff",
                    borderWidth: 2,
                    hoverBackgroundColor: "#0043ff",
                    hoverBorderColor: "#0043ff",
                    data: dataForDataSets,
                }]
            };
            const options = {
                scales: {
                    yAxes: [{
                        stacked: true,
                        gridLines: {
                            display: true,
                            color: "rgba(255,99,132,0.2)"
                        }
                    }],
                    xAxes: [{
                        barPercentage: 0.5,
                        gridLines: {display: false}
                    }]
                },
                maintainAspectRatio: false,
                legend: {
                    labels: {
                        fontSize: 20 // override global
                    }
                }
            };

            new Chart(canvas.getContext('2d'), {
                type: 'bar',
                data: chartData,
                options: options
            });

        })
        .catch(error => {
            console.error('Error:', error);
        });
}
