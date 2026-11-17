export function getClientId(){
    let id = localStorage.getItem("client_id");
    if(!id){
        id = String(Math.floor(Math.random() * 1000));
        localStorage.setItem("client_id", id);

    }
    return id;
}

