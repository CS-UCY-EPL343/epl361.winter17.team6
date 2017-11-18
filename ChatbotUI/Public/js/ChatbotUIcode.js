//links
//http://eloquentjavascript.net/09_regexp.html
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions
nlp = window.nlp_compromise;

var messages = [], //array that hold the record of each string in chat
    lastUserMessage = "", //keeps track of the most recent input string from the user
    botMessage = "", //var keeps track of what the chatbot is going to say
    botName = 'FoodyBot', //name of the chatbot
    Username = 'Customer',
    talking = true; //when false the speach function doesn't work


function chatbotResponse(response) {
    //
    if (!botMessage) botMessage = "i'm confused";
    botMessage = response;
    //botMessage = nlp.sentence(lastUserMessage).replace('[Noun]', 'cat').text() //replace all nouns with cat
    //botMessage = nlp.statement(lastUserMessage).negate().text();   //negate sentense
    //botMessage = nlp.statement(lastUserMessage).to_future().text(); //   to_past    to_present
    //botMessage = nlp.text(lastUserMessage).root();  //makes the sentence simple
    //botMessage = nlp.noun(lastUserMessage).pluralize() //will make a noun plural (for single words)
}

//AUTO SCROLL DOWN
//$("#chatborder").animate({ scrollTop: $("#chatborder").prop('scrollHeight')}, 0);
//$("#chatborder").scrollTop($("#chatborder").prop('scrollHeight'));
//
//OR
// $("#chatborder").scroll(function(){
//     if ($("#chatborder").scrollTop() == $("#chatborder").prop('minHeight')-$("#chatborder").height()){
//         alert("We're at the bottom of the page!!");
//     }
// });
//
// $("#chatborder").scroll(function(){
//     if ($("#chatborder").scrollTop() == $("#chatborder").prop('minHeight')-$("#chatborder").height()){
//         $.ajax({
//             url: "index.html",
//             success: function (data) { $("#chatborder").append(data); },
//             dataType: 'html'
//         });
//     }
// });


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

//runs the keypress() function when a key is pressed
document.onkeypress = keyPress;

//if the key pressed is 'enter' runs the function newEntry()
function keyPress(e) {
    var x = e || window.event;
    var key = (x.keyCode || x.which);
    if (key == 13 || key == 3) {
        //runs this function when enter is pressed
        $.post("http://localhost:4567/hello",
            lastUserMessage,
            function (data, status) {
                //alert("Data: " + data + "\nStatus: " + status);
                console.log(data);
                //botMessage = data;
                newEntry(data);

                console.log(messages.toString());
            });
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


            //if the message from the user isn't empty then run
            if (document.getElementById("chatbox").value != "") {
                //pulls the value from the chatbox ands sets it to lastUserMessage
                lastUserMessage = document.getElementById("chatbox").value;
                //sets the chat box to be clear
                document.getElementById("chatbox").value = "";

                //adds the value of the chatbox to the array messages
                //with current time
                var d = new Date();
                messages.push("<b>" + Username + ":</b> " + lastUserMessage + "<br>" + d.toUTCString());

                //Speech(lastUserMessage);  //says what the user typed outloud
                //sets the variable botMessage in response to lastUserMessage
                //chatbotResponse(data);

                $.post("http://localhost:4567/hello",
                    lastUserMessage,
                    function (data, status) {
                        //alert("Data: " + data + "\nStatus: " + status);
                        console.log(data);

                        //SET THE CHATBOT RESPONSE MESSAGE WITH THE CORRECT FORMAT FROM THE API RESPONSE
                        // FROM JAVA TO HTML
                        if (!botMessage) botMessage = "i'm confused";
                        botMessage = data.replace(/(?:\r\n|\r|\n)/g, '<br />');

                        //add the chatbot's name and message to the array messages
                        messages.push("<b>" + botName + ":</b> " + botMessage + "<br>" + d.toUTCString());

                        // says the message using the text to speech function written below
                        Speech(botMessage);

                        //outputs the last few array elements of messages to html
                        for (var i = 1; i < 8; i++) {
                            if (messages[messages.length - i]) {
                                $('#chatborder').append('<div class="bubble1" >' + messages[messages.length - i - 1] + '</div>');
                                $('#chatborder').append('<div class="bubble2" >' + messages[messages.length - 1] + '</div>');
                                $("#chatborder").scrollTop($("div.chatbox")[0].scrollHeight);
                                //$("#chatborder").scrollTop($("div.chatbox")[0].scrollHeight);
                                //document.getElementById("chatlog" + i).innerHTML = messages[messages.length - i];
                            }
                        }
                        console.log(messages.toString());
                    });
            }
        }
    );
});






//CHATBOX
var count = 0;

function funcx() {
    if (count < 50) {
        count++;
        $('div.chatbox').append('<p class="msg">Test' + count + '</p>');
        $("div.chatbox").scrollTop($("div.chatbox")[0].scrollHeight);
        setTimeout(funcx, 200);
    }
}
