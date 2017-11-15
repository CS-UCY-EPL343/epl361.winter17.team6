//links
//http://eloquentjavascript.net/09_regexp.html
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions
var nlp = window.nlp_compromise;
var messages = [], //array that hold the record of each string in chat
    lastUserMessage = "", //keeps track of the most recent input string from the user
    botMessage = "", //var keeps track of what the chatbot is going to say
    botName = 'FoodyBot', //name of the chatbot
    talking = true, //when false the speach function doesn't work
    response = "";

//this runs each time enter is pressed.
//It controls the overall input and output
function newEntry(data) {
    alert("I GOT IN NEW ENTRY");
    //if the message from the user isn't empty then run
    if (document.getElementById("chatbox").value != "") {
        //pulls the value from the chatbox ands sets it to lastUserMessage
        lastUserMessage = document.getElementById("chatbox").value;
        console.log("The value that the user wrote is " + lastUserMessage + " and the data is " + data );
        //sets the chat box to be clear
        document.getElementById("chatbox").value = data;
        //adds the value of the chatbox to the array messages
        messages.push(lastUserMessage);
        //Speech(lastUserMessage);  //says what the user typed outlou
        //sets the variable botMessage in response to lastUserMessage
        botMessage = data;
        //add the chatbot's name and message to the array messages
        messages.push("<b>" + botName + ": " + botMessage + "</b>");
        // says the message using the text to speech function written below
        Speech(botMessage);
        //outputs the last few array elements of messages to html
        for (var i = 1; i < 8; i++) {
            if (messages[messages.length - i])
                document.getElementById("chatlog" + i).innerHTML = messages[messages.length - i];
        }
    }
}

//text to Speech
//https://developers.google.com/web/updates/2014/01/Web-apps-that-talk-Introduction-to-the-Speech-Synthesis-API
function Speech(say) {
    if ('speechSynthesis' in window && talking) {
        var utterance = new SpeechSynthesisUtterance(say);
        //msg.voice = voices[10]; // Note: some voices don't support altering params
        //msg.voiceURI = 'native';
        //utterance.volume = 1; // 0 to 1
        //utterance.rate = 0.1; // 0.1 to 10
        //utterance.pitch = 1; //0 to 2
        //utterance.text = 'Hello World';
        //utterance.lang = 'en-US';
        speechSynthesis.speak(utterance);
    }
}
// WHEN SEND BTN IS PRESSED
$(document).ready(function () {
    $("#sendbtn").click(function () {
        console.log(lastUserMessage);
        $.post("http://localhost:4567/hello",
            lastUserMessage,
            function (data, status) {

                response = data;
                newEntry(response);
                //alert("Data: " + data + "\nStatus: " + status);
                console.log(data);
                console.log(messages.toString());
            });

    });
});

//clears the placeholder text ion the chatbox
//this function is set to run when the users brings focus to the chatbox, by clicking on it
function placeHolder() {
    document.getElementById("chatbox").placeholder = "";
}

//
// // WHEN KEY IS PRESSED
// //runs the keypress() function when a key is pressed
// document.onkeypress = keyPress;
// //if the key pressed is 'enter' runs the function newEntry()
// function keyPress(e) {
//     var x = e || window.event;
//     var key = (x.keyCode || x.which);
//     if (key == 13 || key == 3) {
//         //runs this function when enter is pressed
//         newEntry();
//     }
//     if (key == 38) {
//         console.log('hi')
//         //document.getElementById("chatbox").value = lastUserMessage;
//     }
// }