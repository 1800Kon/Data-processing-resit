function call() {
    //fetch call
    let urlCall = document.getElementById("urlCall").value;
    let callContent = document.getElementById("callContent").value;
    let displayBox = document.getElementById("displayBox");
    let responseType = document.querySelector(
        'input[name="responseType"]:checked'
    ).value;
    if (
        urlCall.includes("http://localhost:8080/api/") &&
        (urlCall.includes("post") || urlCall.includes("put"))
    ) {
        let method = urlCall.includes("post") ? "POST" : "PUT";
        if (urlCall.length > 0) {
            if (callContent.length > 0) {
                if (responseType == "JSON") {
                    fetch(urlCall, {
                        method: method,
                        body: JSON.stringify(callContent),
                        headers: {
                            Accept: "application/json",
                            "Content-Type": "application/json",
                        },
                    })
                        .then((response) => response.json())
                        .then((data) => {
                            displayBox.innerHTML = JSON.stringify(data);
                        })
                        .catch((error) => (displayBox.innerHTML = error));
                } else if (responseType == "XML") {
                    fetch(urlCall, {
                        method: method,
                        body: callContent,
                        headers: {
                            Accept: "application/xml",
                            "Content-Type": "application/xml",
                        },
                    })
                        .then((response) => response.text())
                        .then((data) => {
                            displayBox.innerHTML = data;
                        })
                        .catch((error) => (displayBox.innerHTML = error));
                }
            } else {
                displayBox.innerHTML = "Please enter some content";
            }
        } else {
            displayBox.innerHTML = "Please enter an url";
        }
    } else {
        if (
            urlCall.includes("http://localhost:8080/api/") &&
            urlCall.includes("getall")
        ) {
            if (responseType == "JSON") {
                fetch(urlCall, {
                    method: "GET",
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json",
                    },
                })
                    .then((response) => response.json())
                    .then((data) => {
                        displayBox.innerHTML = JSON.stringify(data);
                    });
            } else {
                if (responseType == "XML") {
                    fetch(urlCall, {
                        method: "GET",
                        headers: {
                            Accept: "application/xml",
                            "Content-Type": "application/xml",
                        },
                    })
                        .then((response) => response.text())
                        .then((data) => {
                            displayBox.innerHTML = escape(data);
                        });
                }
            }
        } else {
            if (
                urlCall.includes("http://localhost:8080/api/") &&
                urlCall.includes("delete")
            ) {
                fetch(urlCall, {
                    method: "DELETE",
                })
                    .then((response) => response.text())
                    .then((data) => {
                        displayBox.innerHTML = data;
                    });
            }
        }
    }
}

async function graph() {
    let ratings = "http://localhost:8080/api/gameRatings/getall";
    let sales = "http://localhost:8080/api/videogameSales/getall";
    let ratingsResponse = [];
    let salesResponse = [];

    fetch(ratings, {
        method: "GET",
    })
        .then((response) => response.json())
        .then((data) => {
            ratingsResponse = data;
        });

    fetch(sales, {
        method: "GET",
    })
        .then((response) => response.json())
        .then((data) => {
            salesResponse = data;
        });
    await new Promise(r => setTimeout(r, 1000));
    for (let i = 0; i < ratingsResponse.length; i++) {
        if (ratingsResponse[i].platform.toLowerCase() != "wii" || "xbox360") {
            ratingsResponse.splice(i, 1);
        }
    }

    for (let i = 0; i < salesResponse.length; i++) {
        if (salesResponse[i].platform.toLowerCase() != "wii" || "xbox360") {
            salesResponse.splice(i, 1);
        }
    }

    let wiiScore = [];
    let xboxScore = [];

    for (let i = 0; i < ratingsResponse.length; i++) {
        if (ratingsResponse[i].platform.toLowerCase() == "wii") {
            wiiScore.push(ratingsResponse[i].score);
        } else {
            xboxScore.push(ratingsResponse[i].score);
        }
    }

    let wiiSales = [];
    let xboxSales = [];

    for (let i = 0; i < salesResponse.length; i++) {
        if (salesResponse[i].platform.toLowerCase() == "wii") {
            wiiSales.push(salesResponse[i].globalSales);
        } else {
            xboxSales.push(salesResponse[i].globalSales);
        }
    }

    // get average from array
    var averageWiiScoresum = 0;
    var averageWiiScorecount = 0;
    for (var i = 0; i < wiiScore.length; i++) {
        averageWiiScoresum += wiiScore[i];
        averageWiiScorecount++;
    }
    let averageWiiScore = averageWiiScoresum / averageWiiScorecount;

    var averageXboxScoresum = 0;
    var averageXboxScorecount = 0;
    for (var i = 0; i < xboxScore.length; i++) {
        averageXboxScoresum += xboxScore[i];
        averageXboxScorecount++;
    }
    let averageXboxScore = averageXboxScoresum / averageXboxScorecount;

    // get total amount of wii sales
    var totalWiiSalessum = 0;
    for (var i = 0; i < wiiSales.length; i++) {
        totalWiiSalessum += wiiSales[i];
    }
    let totalWiiSales = totalWiiSalessum;

    // get total amount of xbox sales
    var sum = 0;
    for (var i = 0; i < xboxSales.length; i++) {
        sum += xboxSales[i];
    }
    let totalXboxSales = sum;
    await new Promise(r => setTimeout(r, 1000));
    new Chart("myChart", {
        type: "bar",
        data: {
            labels: [
                "Average Wii score",
                "Average Xbox 360 score",
                "Total Wii sales",
                "Total Xbox 360 sales",
            ],
            datasets: [
                {
                    backgroundColor: ["red", "green", "blue", "orange"],
                    data: [
                        averageWiiScore,
                        averageXboxScore,
                        totalWiiSales,
                        totalXboxSales,
                    ],
                },
            ],
        },
        options: {
            legend: {display: false},
            title: {
                display: true,
                text: "Graph of Wii and Xbox 360 sales and ratings",
            },
        },
    });
}
