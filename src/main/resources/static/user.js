const userUrl = 'http://localhost:8080/api/user';


function getUserPage() {
    fetch(userUrl).then(response => response.json()).then(user =>
        getInformationAboutUser(user))
}

function getInformationAboutUser(user) {

    let result = '';
    result =

        `<tr>
    <td>${user.id}</td>
    <td>${user.name}</td>
    <td>${user.salary}</td>
    <td>${user.username}</td>
    <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
</tr>`
    document.getElementById('userTableBody').innerHTML = result;
}

getUserPage();