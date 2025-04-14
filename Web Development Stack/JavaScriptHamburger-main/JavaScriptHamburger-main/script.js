// initializing each element needed
const output = document.getElementById('output');
const button = document.getElementById('generateBurger');
const img = document.getElementsByClassName('chefImage')[0];
const audio = document.getElementById('burgerSong');

//choices for each category
let bunOptions = ['sesame', 'whole grain', 'gluten-free', 'italian'];
let vegetableOptions = ['lettuce', 'tomato', 'onion', 'mushroom'];
let cheeseOptions = ['cheddar', 'swiss', 'blue', 'feta'];
let sauceOptions = ['ketchup', 'mustard', 'mayo', 'hot sauce'];
let meatOptions = ['beef', 'chicken', 'vegetarian', 'lamb'];
let pattyAmountOptions = ['single', 'double', 'triple', 'quadruple'];
let extraToppingsOptions = ['olive', 'pickle', 'hot pepper', 'bell pepper'];

// constructor for burger
function Burger() {

    // random choice of 4 for each type of category
    this.bun = bunOptions[Math.floor(Math.random() * 4)];
    this.vegetable = vegetableOptions[Math.floor(Math.random() * 4)];
    this.cheese = cheeseOptions[Math.floor(Math.random() * 4)];
    this.sauce = sauceOptions[Math.floor(Math.random() * 4)];
    this.meat = meatOptions[Math.floor(Math.random() * 4)];
    this.pattyAmount = pattyAmountOptions[Math.floor(Math.random() * 4)];
    this.extraToppings = extraToppingsOptions[Math.floor(Math.random() * 4)];
}

// function for clicking button
button.addEventListener('click', () => {
    let burger1 = new Burger(); // Create a new Burger object with random ingredients
    if (!audio.paused) return;
    // Create a string describing the burger
    output.textContent = `Your burger has a ${burger1.bun} bun, ${burger1.vegetable} as the vegetable, ${burger1.cheese} cheese, ${burger1.sauce}, a ${burger1.meat} patty, ${burger1.pattyAmount} patty, and ${burger1.extraToppings} as extra topping.`;
    img.style.display = 'none';
    audio.currentTime = 0.25;
    audio.play();
    button.disabled = true;
});

// event listener for audio when it ends
audio.addEventListener('ended', () => {
    button.disabled = false;
});