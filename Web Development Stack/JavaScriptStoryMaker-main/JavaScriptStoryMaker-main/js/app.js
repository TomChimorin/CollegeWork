// Variables to store references to various DOM elements
const textArea = document.querySelector('.big-ass-title'); // The main title area where the sentence will be displayed
const speakButton = document.querySelector('#speak'); // Button to trigger speech synthesis
const resetButton = document.querySelector('#reset-button'); // Button to reset the selection
const randomButton = document.querySelector('#random-button'); // Button to generate a random sentence
const columnArray = document.querySelectorAll('.column .list'); // List of all columns
const buttonArray = document.querySelectorAll('.column button'); // List of all buttons within columns

// Handle the speak button click
speakButton.addEventListener('click', () => {
  const utterThis = new SpeechSynthesisUtterance(textArea.textContent); // Create a speech synthesis utterance with the text from textArea
  window.speechSynthesis.speak(utterThis); // Speak the utterance
})

// Handle the reset button click
resetButton.addEventListener('click', () => {
  const activeWords = document.querySelectorAll('.list .active'); // Get all active words

  activeWords.forEach((activeWord) => {
    activeWord.classList.remove('active'); // Remove the active class from each word
  })

  textArea.textContent = "generate a sentence"; // Reset the text area content
})

// Handle the random button click
randomButton.addEventListener('click', () => {
  columnArray.forEach((column) => {
    selectRandomWord(column); // Select a random word for each column
  })
})

// Attach event listener for button clicks within columns
buttonArray.forEach((button) => {
  button.addEventListener('click', () => {
    selectRandomWord(button.nextElementSibling); // Select a random word in the corresponding list
  });
})

// Function to select a random word from a list
const selectRandomWord = (wordList) => {
  // Get the previous active word
  const previousActiveWord = document.querySelector('.list .active');

  // Remove active class from all words in the list
  for (let i = 0; i < wordList.children.length; i++) {
    wordList.children[i].classList.remove('active');
  }

  let selectedWord = null;
  do {
    const random = Math.floor(Math.random() * wordList.children.length); // Generate a random index
    selectedWord = wordList.children[random]; // Select a word at the random index
  } while (previousActiveWord && selectedWord === previousActiveWord); // Ensure the selected word is different from the previous active word

  selectedWord.classList.add('active'); // Add the active class to the selected word
  updateTextArea(); // Update the text area with the new selection
}

// Function to update the text area with the active words
const updateTextArea = () => {
  const activeWords = document.querySelectorAll('.list .active'); // Get all active words

  if (activeWords.length !== 5) {
    return; // Ensure all five categories have a selected word before updating the text area
  }

  let text = '';
  activeWords.forEach((word) => {
    text += word.children[1].textContent + ' '; // Append each active word's text content to the sentence
  });

  textArea.textContent = text; // Update the text area with the new sentence
}
