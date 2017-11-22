//links
//http://eloquentjavascript.net/09_regexp.html
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions
nlp = window.nlp_compromise;
var DEBUG = true;
var messages = [], //array that hold the record of each string in chat
    lastUserMessage = "", //keeps track of the most recent input string from the user
    botMessage = "", //var keeps track of what the chatbot is going to say
    botName = 'FoodyBot', //name of the chatbot
    users = ['Marios', 'Andri', 'Kotsios', 'Mary', 'Mark', 'Andreas'],
    user = {
        username: 0,
        token: 0
    },
    currentUser = 'Marios',
    token = 0,
    talking = false, //when false the speach function doesn't work
    d = new Date(),
    days = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

// The hello message from the chatbot to the user
$(document).ready(function () {
    document.getElementById("chatbox").disabled = false;
    currentUser = users[Math.floor(Math.random() * 6)];
    botMessage = "Hello " + currentUser + "! I'm FoodyBot! Please enter one of the following:" +
        "<br />\"souvlakia\". To find a specific restaurant which contains souvlakia." +
        "<br />\"burger\". To find a specific restaurant which contains burgers." +
        "<br />\"sandwich\". To find a specific restaurant which contains sandwiches." +
        "<br />\"history\". To get the current chat log." +
        "<br />..." +
        "<br />Please enter one of the above choices.";
    botMessage = "<b>" + botName + ":</b> " +
        "<span id='chattimestamp'>" + days[d.getDay()] + " at " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "</span>" +
        "<p>" + botMessage + "</p>";

    //add the chatbot's name and message to the array messages
    d = new Date();
    messages.push(botMessage);

    // CONVERSATION INITIALIZATION
    var jsonReqBody = {
        'username': currentUser,
        'timestamp': d.getTime()
    };
    // GET USER TOKEN
    $.post("http://localhost:4567/init",
        jsonReqBody,
        function (data, status) {
            token = data.token;
            // CURRENT USER SIGNED IN
            $('#userlist').append("<div id='currusertoken'>" + "<b>" + "Current user signed in token: " + "</b> " + token + "</div>");
            $('#userlist').append("<div id='currusername'>" + "<b>" + "Current username: " + "</b> " + currentUser + "</div>");
            if (DEBUG) {
                console.log("The token received from server is " + token);
            }
            //chatResponse(data);
        });

    // says the message using the text to speech function written below
    Speech(botMessage);
    $('#chatborder').append('<ul class="bubble2" >' + messages[0] + '</ul>');
    $('#chatborder').scrollTop($('#chatborder')[0].scrollHeight);
});

// WHEN SEND BTN IS PRESSED
$(document).ready(function () {
    $("#sendbtn").click(function () {
            console.log(lastUserMessage);
            newEntry();
        }
    );
});

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
        var usrmessage = "<b>" + currentUser + ":</b> " +
            "<span id='chattimestamp'>" + days[d.getDay()] + " at " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds() + "</span>" +
            "<p>" + lastUserMessage + "</p>";
        messages.push(usrmessage);

        // VIEW THE MESSAGE IN THE USER INTERFACE
        $('#chatborder').append('<ul class="bubble1" >' + messages[messages.length - 1] + '</ul>');

        var jsonReqBody = {
            'token': token,
            'usrmsg': lastUserMessage,
            'msgtostore': usrmessage,
            'timestamp': d.getTime()
        };
        $.post("http://localhost:4567/getmsg",
            jsonReqBody,
            function (data, status) {
                if (DEBUG) {
                    console.log("The json object response received from the server is ");
                    console.log(data);
                }
                chatResponse(data.responsemsg);
            });
    }
}

function chatResponse(data) {
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

// THE POST REQUEST AND RESPONSE FOR A SELECTION OF A RESTAURANT
function sendId(id) {
    //alert(id);
    var jsonReqBody = {
        'token': token,
        'usrmsg': "usr_selection res_id=" + id
    };
    $.post("http://localhost:4567/getmsg",
        jsonReqBody,
        function (data, status) {

            if (DEBUG) {
                alert("usr_selection res_id=" + id);
                console.log(data);
            }
            chatResponse(data.responsemsg);
            document.getElementById("chatbox").disabled = false;

        });
    document.getElementById("chatbox").disabled = true;

    // THE SELECTED RESTAURANT
    alert(document.getElementById("clickable-rest-" + id).innerText);
}


var selectedItems = [],
    selItemsIDs = [];

// THE POST REQUEST AND RESPONSE FOR A SELECTION OF A RESTAURANT
function sendMenuItemId(id) {
    $('#basket').empty();
    if (DEBUG) {
        //alert(id);
    }
    selectedItems.push("usr_selection mi_id=" + id);
    alert("Item: " + document.getElementById("clickable-mi-" + id).innerText + " ADDED TO BASKET!");

    if (selectedItems.length == 0) {
        $('#chatborder').append('<button id=\"submitbtn\" onclick=\"newEntry()\">SUBMIT</button>');
    }

    selItemsIDs.push(id);
    var items = [], itemNumber = [];
    [items, itemNumber] = countItems(selItemsIDs);
    for (var i = 0; i < items.length; i++) {
        $('#basket').append('<ul class="item" >' +
            itemNumber[i] + " x " + document.getElementById("clickable-mi-" + items[i]).innerText + '</ul>');
        $('#basket').scrollTop($('#basket')[0].scrollHeight);
    }


    // var jsonReqBody = {
    //     'token' : token,
    //     'usrmsg' : "usr_selection mi_id="+id
    // };
    //

    // AVOID DUPLICATES
    // if(selectedItems.length ==0){
    //     selectedItems.push("usr_selection mi_id="+id);
    // }
    // var tf = true , i;
    // for(i=0; i<selectedItems.length; i++){
    //     if(selectedItems[i] == ("usr_selection mi_id="+id)){
    //         alert("ERROR! You already entered that item!");
    //         tf = false;
    //         break;
    //     }
    // }
    // if(tf){
    //     selectedItems.push("usr_selection mi_id="+id);
    // }
    // var s = "";
    // for(i=0; i<selectedItems.length; i++){
    //     s.append(selectedItems[i]);
    // }
    // alert(s);


    // $.post("http://localhost:4567/getmsg",
    //     jsonReqBody,
    //     function (data, status) {
    //
    //         if (DEBUG) {
    //             alert("usr_selection res_id="+id);
    //             console.log(data);
    //         }
    //         chatResponse(data.responsemsg);
    //         document.getElementById("chatbox").disabled = false;
    //
    //     });
    //document.getElementById("chatbox").disabled = true;

    // THE SELECTED MENU ITEM
    //alert(document.getElementById("clickable-mi-"+id).innerText);
}

// WHEN SUBMIT BTN IS PRESSED
// $(document).ready(function () {
//     $("#submitbtn").click(function () {
//             console.log(lastUserMessage);
//             newEntry();
//         }
//     );
// });

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


//-------------------------------------------------------
// USEFUL FUNCTIONS:
//-------------------------------------------------------
/**
 * TOGGLE FOR THE VIEW BASKET
 */
function toggle() {
    var x = document.getElementById("product-list");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

//text to Speech
//https://developers.google.com/web/updates/2014/01/Web-apps-that-talk-Introduction-to-the-Speech-Synthesis-API
function Speech(say) {
    if ('speechSynthesis' in window && talking) {
        var utterance = new SpeechSynthesisUtterance(say);
        //msg.voice = voices[1]; // Note: some voices don't support altering params
        //utterance.voiceURI = 'native';
        //utterance.volume = 1; // 0 to 1
        //utterance.rate = 0.1; // 0.1 to 10
        //utterance.pitch = 1; //0 to 2
        //utterance.text = 'Hello World';
        //utterance.lang = 'en-US';
        speechSynthesis.speak(utterance);
    }
}

// WHEN THE FOOD MENU IS PRESENTED
$(document).ready(function () {
    $("clickable-mi").click(function () {
            console.log(lastUserMessage);
            newEntry();
        }
    );
});

/**
 * Used to count the number of items in the basket individually
 * @param arr
 * @returns {[null,null]}
 */
function countItems(arr) {
    var a = [], b = [], prev;
    arr.sort();
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] !== prev) {
            a.push(arr[i]);
            b.push(1);
        } else {
            b[b.length - 1]++;
        }
        prev = arr[i];
    }
    return [a, b];
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
