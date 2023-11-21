//This file exists as a reference to the function we have hosted in google cloud functions to make the API call to dezgo. We are not calling it directly anywhere in the app.

// const functions = require("@google-cloud/functions-framework");

// const axios = require("axios");
// const FormData = require("form-data");

// async function makeRequest(req) {
//   const url = "https://api.dezgo.com/text2image";
//   const apiKey = "api_key_here";

//   const prompt = req.query.prompt
//     ? req.query.prompt
//     : "colorful color goblin orange skinny fangs";
//   console.log(prompt);

//   const form = new FormData();
//   form.append("prompt", prompt);

//   const headers = {
//     "X-Dezgo-Key": apiKey,
//     ...form.getHeaders(),
//   };

//   try {
//     const response = await axios.post(url, form, {
//       headers,
//       responseType: "arraybuffer",
//     });
//     return response.data;
//   } catch (error) {
//     console.error("Error making the request:", error.message);
//     throw error;
//   }
// }

// functions.http("getImage", (req, res) => {
//   // Example usage
//   makeRequest(req)
//     .then((result) => res.send(result))
//     .catch((error) => res.error(error));
// });
