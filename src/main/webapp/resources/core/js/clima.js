const API_KEY = "17ba018d5a299310d2a08723910a7584";
const cityInput = document.getElementById("cityInput");
const countryInput = document.getElementById("countryInput");
const getWeatherBtn = document.getElementById("getWeatherBtn");
const weatherInfoDiv = document.querySelector("#weather-info");
const currentWeatherSpan = document.getElementById("currentWeather");

getWeatherBtn.addEventListener("click", () => {
  const city = cityInput.value.trim();
  const country = countryInput.value.trim().toUpperCase();

  if(city === "") {
    alert("Por favor, ingrese el nombre de una ciudad");
    return;
  }

  if(country === "") {
    alert("Por favor, ingrese el codigo del pais, por ejemplo AR");
    return;
  }

  const query = `${encodeURIComponent(city)},${encodeURIComponent(country)}`;
  const API_URL = `https://api.openweathermap.org/data/2.5/weather?q=${query}&appid=${API_KEY}&units=metric&lang=es`;

  fetch(API_URL)
    .then((response) => {
      if(!response.ok) {
        throw new Error(
          `Error ${response.status}: Ciudad no encontrada.`
        );
      }
      return response.json();
    })
    .then((data) => {
      displayWeather(data);
    })
    .catch((error) => {
      weatherInfoDiv.innerHTML = `<p style="color: red;">${error.message}</p>`;
    });
});

function displayWeather(data) {
  const cityName = data.name;
  const temp = Math.round(data.main.temp);

  currentWeatherSpan.textContent = `${cityName}, ${temp}\u00B0C`;
  weatherInfoDiv.innerHTML = "";
}
