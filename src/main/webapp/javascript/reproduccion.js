let baseUrl = "http://localhost:8080/ISDCM_3/api/video";
let userId;
let videoId;
let isPaused = false;
let isEnded = false;

function onDocumentReady(id_user) {
    userId = id_user;
    videoId = new URLSearchParams(window.location.search).get('video');
    videoId = parseInt(videoId);

    try {
        if (isNaN(videoId)) onErrorGet("Identificador del vídeo no válido. Haz click aquí para volver a la pantalla principal.");
        else getVideo();
    } catch (e) {
        onErrorGet(e.toString());
    }
}

function onClickPlay() {
    let body = {
        id_video : userId,
        id_user : videoId
    };

    let options = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    };

    fetch(baseUrl, options)
        .then(res => res.json())
        .then(onResponse);
}

function getVideo() {
    var url = new URL(baseUrl);
    var params = { id_video: videoId };
    url.search = new URLSearchParams(params).toString();
    
    let options = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
        }
    };

    fetch(url, options)
        .then(res => res.json())
        .then(onResponseGet);
}

function increasePlays() {
    let body = {
        id_video : videoId,
        id_user : userId
    };

    let options = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body)
    };

    fetch(baseUrl, options)
        .then(res => res.json())
        .then(onResponsePut);
}

function onPause() {
    console.log("pausing video");
    isPaused = true;
}

function onPlay() {
    if (!isPaused || isEnded) increasePlays();
    isPaused = false;
    isEnded = false;
}

function onEnd() {
    isEnded = true;
}

function onResponseGet(response) {
    if (response.message) onErrorGet("Identificador del vídeo no válido. Haz click aquí para volver a la pantalla principal.");
    else {
        console.log("success" + JSON.stringify(response));

        const provider = getProvider(response.path)

        if (provider) {
            document.getElementById("player").setAttribute("data-plyr-embed-id", response.path);
            document.getElementById("player").setAttribute("data-plyr-provider", provider);
            let player = Plyr.setup(".js-player");
            player[0].on("play", onPlay);
            player[0].on("pause", onPause);
            player[0].on("ended", onEnd);
        } else onErrorGet("Proveedor del vídeo no válido. Haz click aquí para volver a la pantalla principal.");

        document.getElementById("views").textContent = response.plays;
        document.getElementById("time").textContent = response.duration;
    }
}

function getProvider(path) {
    if (path) {
        if (path.includes("youtube.com")) return "youtube";
        else if (path.includes("vimeo.com")) return "vimeo";
        else return undefined;
    } else return undefined;
}

function onResponsePut(response) {
    if (!response.message) {
        console.log("success" + JSON.stringify(response));
    
        document.getElementById("views").textContent = response.plays;
        document.getElementById("time").textContent = response.duration;
    } else console.log(response.message);
    
}

function onErrorGet(message) {
    console.log("error: " + message);
    
    if (message) document.getElementById("error-message").textContent = message;
    document.getElementById("content").setAttribute("hidden", "");
    document.getElementById("error").removeAttribute("hidden");
}