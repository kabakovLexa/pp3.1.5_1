const url = 'http://localhost:8080/api/admin';


async function getRoles() {
    return await fetch("http://localhost:8080/api/admin/roles").then(response => response.json());
}

function listRoles() { //GET Response for Roles and write in HTML
    let tmp = '';
    getRoles().then(roles => roles.forEach(role => {
        tmp += `<option value="${role.id}">${role.name}</option>`;
    })).then(r => {
        console.log('listRoles')
        document.getElementById('editRole').innerHTML = tmp;
        document.getElementById('deleteRole').innerHTML = tmp;
    })
}

listRoles();

function getUserData() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            loadTable(data)
        })
}


function getAllUsers() {
    fetch(url).then(response => response.json()).then(user =>
        loadTable(user))
}

function loadTable(listAllUsers) {
    let res = '';
    for (let user of listAllUsers) {
        res +=
            `<tr>
                <td>${user.id}</td>             
                <td>${user.name}</td>
                <td>${user.salary}</td>
                <td>${user.username}</td>
                <td>${user.roles ? user.roles.map(role => " " + role.name.substring(5)) : ""}</td>
                <td>
                    <button class="btn btn-info" type="button"
                    data-bs-toggle="modal" data-bs-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                    <button class="btn btn-danger" type="button"
                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`;
    }
    document.getElementById('tableBodyAdmin').innerHTML = res;
}
getAllUsers();



// Новый юзер
document.getElementById('newUserForm').addEventListener('submit', (e) => {
    e.preventDefault()
    let role = document.getElementById('role_select')
    let rolesAddUser = []
    let rolesAddUserValue = ''
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            rolesAddUser.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML})
            rolesAddUserValue += role.options[i].innerHTML
        }
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            name: document.getElementById('newName').value,
            salary: document.getElementById('newSalary').value,
            username: document.getElementById('newUserName').value,
            password: document.getElementById('newPassword').value,
            roles: rolesAddUser
        })
    })
        .then((response) => {
            if (response.ok) {
                getUserData()
                document.getElementById("all-users-tab").click()
            }
        })
})


//Изменение юзера
function editModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {

            document.getElementById('editId').value = u.id;
            document.getElementById('editNameU').value = u.name;
            document.getElementById('editSalary').value = u.salary;
            document.getElementById('editUsername').value = u.username;
            document.getElementById('editPassword').value = "****";

        })
    });
}
async function editUser() {
    const form_ed = document.getElementById('modalEdit');

    let idValue = document.getElementById("editId").value;
    let nameValue = document.getElementById('editNameU').value;
    let salaryValue = document.getElementById('editSalary').value
    let userNameValue = document.getElementById('editUsername').value;
    let passwordValue = document.getElementById("editPassword").value;
    let listOfRole = [];
    for (let i = 0; i < form_ed.role.options.length; i++) {
        if (form_ed.role.options[i].selected) {
            let tmp = {};
            tmp["id"] = form_ed.role.options[i].value
            listOfRole.push(tmp);
        }
    }
    let user = {
        id: idValue,
        name: nameValue,
        salary: salaryValue,
        username: userNameValue,
        password: passwordValue,
        roles: listOfRole
    }
    await fetch(url + '/' + user.id, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
    closeModal()
    getUserData()
}


// Удаление юзера
function deleteModal(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(u => {
            document.getElementById('deleteId').value = u.id;
            document.getElementById('deleteNameU').value = u.name;
            document.getElementById('deleteSalary').value = u.salary;
            document.getElementById('deleteUserName').value = u.username;

        })
    });
}
async function deleteUser() {
    const id = document.getElementById("deleteId").value
    console.log(id)
    let urlDel = url + "/" + id;
    let method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        }
    }

    fetch(urlDel, method).then(() => {
        closeModal()
        getUserData()
    })
}

function closeModal() {
    // document.getElementById("editClose").click()
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}
