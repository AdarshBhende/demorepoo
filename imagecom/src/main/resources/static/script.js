
function previewImage(event, previewId) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const imgElement = document.getElementById(previewId);
            imgElement.src = e.target.result;
            imgElement.style.display = "block"; // Make the image visible
        };
        reader.readAsDataURL(file);
    }
}

function convertToBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result.split(",")[1]); // Remove the initial "data:image/png;base64,"
    reader.onerror = (error) => reject(error);
  });
}

function compareImages() {
  let image1 = document.getElementById("image1").files[0];
  let image2 = document.getElementById("image2").files[0];

  if (!image1 || !image2) {
    document.getElementById("result").innerText = "Please upload both images.";
    return;
  }

  // Convert both images to Base64
  Promise.all([convertToBase64(image1), convertToBase64(image2)])
    .then(([base64Image1, base64Image2]) => {
      // Create the request body
      console.log(base64Image2);
      let requestBody = {
        image1: base64Image1,
        image2: base64Image2,
      };

      // Send the request using fetch API
      fetch("http://localhost:8080/api/compare", {
        method: "POST",
        headers: {
          "Content-Type": "application/json", // Sending JSON data
        },
        body: JSON.stringify(requestBody), // Stringify the JSON object
      })
        .then((response) => response.text()) // Change to `.json()` if the response is JSON
        .then((data) => {
          document.getElementById("result").innerText = data;
        })
        .catch((error) => {
          console.error("Error:", error);
          document.getElementById("result").innerText = "An error occurred.";
        });
    })
    .catch((error) => {
      console.error("Error converting images to Base64:", error);
      document.getElementById("result").innerText =
        "An error occurred while processing the images.";
    });
}
