function compareImages() {
    let image1 = document.getElementById('image1').files[0];
    let image2 = document.getElementById('image2').files[0];

    if (!image1 || !image2) {
        document.getElementById('result').innerText = "Please upload both images.";
        return;
    }

    // Create FormData and append files
    let formData = new FormData();
    formData.append("image1", image1);
    formData.append("image2", image2);

    // Send request using fetch API
    fetch("http://localhost:8080/api/compare", {
        method: "POST",
        body: formData  // No need to set headers for FormData
    })
    .then(response => response.text()) // Change to `.json()` if the response is JSON
    .then(data => {
        document.getElementById('result').innerText = data;
    })
    .catch(error => {
        console.error("Error:", error);
        document.getElementById('result').innerText = "An error occurred.";
    });
}
