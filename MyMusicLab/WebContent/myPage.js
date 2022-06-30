var xmlHttpRequest;

function sendWithGetMethod() {
  var url = "myPage";

  xmlHttpRequest = new XMLHttpRequest();
  xmlHttpRequest.onreadystatechange = receive;
  xmlHttpRequest.open("GET", url, true);
  xmlHttpRequest.send(null);
}

function receive() {
  if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
    var response = JSON.parse(xmlHttpRequest.responseText);
    if (response != null) {
      var newText = document.createTextNode(response.username);
      document.getElementById("username").appendChild(newText);

      newText = document.createTextNode(response.music_count);
      document.getElementById("music_count").appendChild(newText);

      newText = document.createTextNode(response.friend_count);
      document.getElementById("friend_count").appendChild(newText);

    } else {
      alert("ユーザー情報を取得できませんでした");
    }
  }
}

window.addEventListener("load", function() {
    sendWithGetMethod();
}, false);
