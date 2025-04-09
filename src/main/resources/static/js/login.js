const loginBtn = document.querySelector("button.login-btn");
loginBtn.addEventListener('click', () => {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const container = document.querySelector('div.login-container')
    const errMsg = document.createElement('p');

    if (errMsg) { errMsg.remove(); }

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
    })
        .then((response) => {
            if(response.ok){
                alert('로그인 성공')
                window.location.href='/home'
            }else{
                errMsg.innerText = '아이디 또는 패스워드를 입력해주세요.'
                container.appendChild(errMsg)
            }
        })
})