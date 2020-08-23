async function init()
{    
    const responseCheckLogin = await fetch('/check-login');
    const loggedIn = await responseCheckLogin.json();

    if(!loggedIn)
    {
        const responseLogin = await fetch('/login');
        const url = await responseLogin.text();
        document.getElementById("loginUrl").innerHTML = url;
    }
}