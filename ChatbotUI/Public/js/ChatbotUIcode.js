//links
//http://eloquentjavascript.net/09_regexp.html
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions
nlp = window.nlp_compromise;

var messages = [], //array that hold the record of each string in chat
    lastUserMessage = "", //keeps track of the most recent input string from the user
    botMessage = "", //var keeps track of what the chatbot is going to say
    botName = 'FoodyBot', //name of the chatbot
    Username = 'Marios',
    talking = false, //when false the speach function doesn't work
    d = new Date(),
    days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

// CURRENT USER SIGNED IN
$(document).ready(function () {
    $('#userlist').append("<p id='curruser'>" + "<b>" + "Current user signed in : " + "</b> " + Username + "</p>");
});

// The hello message from the chatbot to the user
$(document).ready(function () {
    botMessage = "Hello " + Username + "! I'm FoodyBot! Please enter one of the following:"+
        "<br />1. To find a specific restaurant." +
        "<br />2. To get the current chat log." +
        "<br />..." +
        "<br />Please enter one of the above numbers.";
    //add the chatbot's name and message to the array messages
    d = new Date();
    messages.push("<b>" + botName + ":</b> " +
        "<span id='chattimestamp'>" + days[d.getDay()] + " at " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "</span>" +
        "<p>" + botMessage + "</p>");

    // says the message using the text to speech function written below
    Speech(botMessage);
    $('#chatborder').append('<ul class="bubble2" >' + messages[0] + '</ul>');
    $('#chatborder').scrollTop($('#chatborder')[0].scrollHeight);
});


//text to Speech
//https://developers.google.com/web/updates/2014/01/Web-apps-that-talk-Introduction-to-the-Speech-Synthesis-API
function Speech(say) {
    if ('speechSynthesis' in window && talking) {
        var utterance = new SpeechSynthesisUtterance(say);
        //msg.voice = voices[1]; // Note: some voices don't support altering params
        //msg.voiceURI = 'native';
        //utterance.volume = 1; // 0 to 1
        //utterance.rate = 0.1; // 0.1 to 10
        //utterance.pitch = 1; //0 to 2
        //utterance.text = 'Hello World';
        //utterance.lang = 'en-US';
        speechSynthesis.speak(utterance);
    }
}

function newEntry() {

//if the message from the user isn't empty then run
    if (document.getElementById("chatbox").value != "") {

        //pulls the value from the chatbox ands sets it to lastUserMessage
        lastUserMessage = document.getElementById("chatbox").value;
        //sets the chat box to be clear
        document.getElementById("chatbox").value = "";

        //adds the value of the chatbox to the array messages
        //with current time
        d = new Date();
        messages.push("<b>" + Username + ":</b> " +
            "<span id='chattimestamp'>" + days[d.getDay()] + " at " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "</span>" +
            "<p>" + lastUserMessage + "</p>");

        $('#chatborder').append('<ul class="bubble1" >' + messages[messages.length - 1] + '</ul>');

        $.post("http://localhost:4567/hello",
            lastUserMessage,
            function (data, status) {
                chatResponse(data);
            });
    }
}

function chatResponse(data){
    //alert("Data: " + data + "\nStatus: " + status);
    console.log(data);

    //SET THE CHATBOT RESPONSE MESSAGE WITH THE CORRECT FORMAT FROM THE API RESPONSE
    // FROM JAVA TO HTML
    if (!botMessage) botMessage = "i'm confused";
    botMessage = data.replace(/(?:\r\n|\r|\n)/g, '<br />');

    //add the chatbot's name and message to the array messages
    d = new Date();
    messages.push("<b>" + botName + ":</b> " +
        "<span id='chattimestamp'>" + days[d.getDay()] + " at " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "</span>" +
        "<p>" + botMessage + "</p>");

    // says the message using the text to speech function written below
    Speech(botMessage);

    //outputs the last few array elements of messages to html
    $('#chatborder').append('<ul class="bubble2" >' + messages[messages.length - 1] + '</ul>');
    $('#chatborder').scrollTop($('#chatborder')[0].scrollHeight);
    console.log(messages.toString());
}



//runs the keypress() function when a key is pressed
document.onkeypress = keyPress;
//if the key pressed is 'enter' runs the function newEntry()
function keyPress(e) {
    var x = e || window.event;
    var key = (x.keyCode || x.which);
    if (key == 13 || key == 3) {
        console.log(lastUserMessage);
        //runs this function when enter is pressed
        newEntry();
    }
    if (key == 38) {
        console.log('hi')
        //document.getElementById("chatbox").value = lastUserMessage;
    }
}

//clears the placeholder text ion the chatbox
//this function is set to run when the users brings focus to the chatbox, by clicking on it
function placeHolder() {
    document.getElementById("chatbox").placeholder = "";
}

// PREVENTS REFRESHING WHEN PRESSING ENTER
$(function () {
    $("form").submit(function () {
        return false;
    });
});

// WHEN SEND BTN IS PRESSED
$(document).ready(function () {
    $("#sendbtn").click(function () {
            console.log(lastUserMessage);
            newEntry();
        }
    );
});




// THE POST REQUEST AND RESPONSE FOR A SELECTION OF A RESTAURANT
function sendId(id){
    //alert(id);
    $.post("http://localhost:4567/hello",
        "usr_selection res_id#"+id,
        function (data, status) {
            alert("usr_selection res_id#"+id);
            chatResponse(data);
            document.getElementById("chatbox").disabled = false;
            console.log(data);
        });
    document.getElementById("chatbox").disabled = true;

    // THE SELECTED RESTAURANT
    alert(document.getElementById("clickable-rest-"+id).innerText);
}

//
// $(document).ready(function () {
//     document.getElementById("chatbox").disabled = false;
//     $('clickable-rest').click(function () {
        //document.getElementById("chatbox").disabled = true;
        //alert("hey");
       // console.log("ghfjfg");
        //$('#chatborder').append('<ul class="bubble2" >' + "WOW" + '</ul>');
            //document.getElementById("chatbox").value = $('#clickable-rest').onclick;
//         }
//     );
// });