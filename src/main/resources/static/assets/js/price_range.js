// Function to update the labels and input values based on the range input value
function updateValues() {
  var range = document.getElementById('price_range');
  var minLabel = document.getElementById('min_price');
  var maxLabel = document.getElementById('max_price');
  var valueInput = document.getElementById('price_value');

  var value = parseInt(range.value);
  var min = parseInt(range.min);
  var max = parseInt(range.max);

  minLabel.textContent = range.min;
  maxLabel.textContent = range.max;
  valueInput.value = value;

  valueInput.min = min;
  valueInput.max = max;
}

  // Add listener to the range input
  var rangeInput = document.getElementById('price_range');
  rangeInput.addEventListener('input', function() {
    var valueInput = document.getElementById('price_value');
    valueInput.value = this.value;
  });

  // Add listener to the number input
  var valueInput = document.getElementById('price_value');
  valueInput.addEventListener('input', function() {
    var rangeInput = document.getElementById('price_range');
    rangeInput.value = parseInt(this.value);
  });

  // Call the updateValues function to set the initial values
  updateValues();