function call() {
  //fetch call
  let urlCall = document.getElementById("urlCall").value;
  let callContent = document.getElementById("callContent").value;
  let displayBox = document.getElementById("displayBox");
  let responseType = document.querySelector('input[name="responseType"]:checked').value;
  if (urlCall.includes("http://localhost:8080/api/") && (urlCall.includes("post") || urlCall.includes("put"))) {
      let method = urlCall.includes("post") ? "POST" : "PUT";
      if (urlCall.length > 0) {
            if (callContent.length > 0) {
                if (responseType == "JSON") {
                    fetch(urlCall, {
                        method: method,
                        body: JSON.stringify(callContent),
                        headers: {
                            "Content-Type": "application/json"
                        }
                    })
                        .then(response => response.json())
                        .then(data => {
                            displayBox.innerHTML = JSON.stringify(data);
                        })
                        .catch(error => displayBox.innerHTML = error);
                } else if (responseType == "XML") {
                    fetch(urlCall, {
                        method: method,
                        body: callContent,
                        headers: {
                            "Content-Type": "application/xml"
                        }
                    })
                        .then(response => response.text())
                        .then(data => {
                            displayBox.innerHTML = data;
                        })
                        .catch(error => displayBox.innerHTML = error);
                }
            } else {
                displayBox.innerHTML = "Please enter some content";
            }
        } else {
            displayBox.innerHTML = "Please enter an url";
        }
    } else {
        if (urlCall.includes("http://localhost:8080/api/") && (urlCall.includes("getall"))) {
            if (responseType == "JSON") {
            fetch(urlCall, {
                method: "GET",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                }
            })
            .then(response => response.json())
            .then(data => {
                displayBox.innerHTML = JSON.stringify(data);
            })
            } else {
                if (responseType == "XML") {
                fetch(urlCall, {
                    method: "GET",
                    headers: {
                        "Accept": "application/xml",
                        "Content-Type": "application/xml"
                    }
                })
                    .then(response => response.text())
                    .then(data => {
                        displayBox.innerHTML = escape(data);
                    })
                }
            }
        } else {
            if(urlCall.includes("http://localhost:8080/api/") && (urlCall.includes("delete"))){
                fetch(urlCall, {
                    method: "DELETE",
                })
                    .then(response => response.text())
                    .then(data => {
                        displayBox.innerHTML = data;
                    })
            }
        }
    }
}