// Getting all input fields of customerDetails.
let fname = document.getElementById("fname");
let lname = document.getElementById("lname");
let street = document.getElementById("street");
let add = document.getElementById("add");
let city = document.getElementById("city");
let state = document.getElementById("state");
let email = document.getElementById("email");
let phone = document.getElementById("phone");
let addUserbtn = document.getElementById("addCustomer");
let updateUserbtn = document.getElementById("updateCustomer");

// Getting all input fields of Sync (Login).
let loginId = document.getElementById("loginId")
let loginPassword = document.getElementById("loginPassword")
let loginButton = document.getElementById("loginButton")

// Getting table element by its id
let table = document.getElementById("table");
let deleteBtn = document.getElementsByClassName("sp1");
let editBtn = document.getElementsByClassName("sp2");

// Default backend URL -- (Backend PORT - 8080)
const ServerPort = "http://localhost:8080/customer";

// Main Data
let mData;

//Fetching all the customers from the database
fetch(`${ServerPort}/get-customer-list`)
    .then(response => response.json())
    .then(data => { mData = data; displayCustomers(data) })
    .catch(err => console.log(err))

// Displaying all the fetched data
function displayCustomers(data) {

    // Adding Table Heading
    let trHeading = document.createElement("tr");
    for (let x in data[0]) {
        if (x !== "uuid" && x !== "street") {
            trHeading.innerHTML += `<th>${x[0].toUpperCase() + x.slice(1,).toLowerCase()}</th>`
        }
    }
    trHeading.innerHTML += "<th>Actions</th>"
    table.appendChild(trHeading);

    //Adding Table Data
    for (let i = 0; i < data.length; i++) {
        let uuid = data[i].uuid;
        let trData = document.createElement("tr");
        trData.innerHTML = `<td>${data[i].first_name}</td>
                                <td>${data[i].last_name}</td>
                                <td>${data[i].address}</td>
                                <td>${data[i].city}</td>
                                <td>${data[i].state}</td>
                                <td>${data[i].email}</td>
                                <td>${data[i].phone}</td>
                                <td><span class="material-symbols-outlined sp1" onClick="deleteFun(event,${i})">remove</span>
                                <span class="material-symbols-outlined sp2" onClick="editFun(event,${i})" data-bs-toggle="modal" data-bs-target="#exampleModal1">edit</span></td>`
        table.appendChild(trData);
    }
}

//Onclick of AddUserbutton userDetails will get stored in the database.
addUserbtn.addEventListener('click', function (e) {
    if (fname.value == "" || lname.value == "" || street.value == "" || add.value == "" || city.value == "" || state.value == "" || email.value == "" || phone.value == "") {
        alert("Please fill all the details!");
    } else {
        let userObj = {
            "first_name": fname.value,
            "last_name": lname.value,
            "street": street.value,
            "address": add.value,
            "city": city.value,
            "state": state.value,
            "email": email.value,
            "phone": phone.value
        }
        // console.log(userObj);
        // console.log(`${ServerPort}/add-customer`);
        fetch(`${ServerPort}/add-customer`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userObj)
        })
            .then(response => response.text())
            .then(data => {
                alert("Status : " + data)
            })
            .catch(error => {
                alert("Error : " + error);
                console.error('Error:', error)
            });
        location.reload();
    }
});

// Deleting customer
function deleteFun(event, i) {
    let response = confirm("Are you sure ?")
    if (response) {
        let uuid = mData[i].uuid;
        fetch(`${ServerPort}/delete-customer?id=${uuid}`, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.text())
            .then(data => alert("Status : " + data))
            .catch(err => { alert("Error : " + err); console.log(err) })

        location.reload();
    }
}

//On the click of Edit icon opening a modal as well as adding existing data into
//empty input fields.
let editUuid;
function convertBtnId() {
    fname.value = "";
    lname.value = "";
    street.value = "";
    add.value = "";
    city.value = "";
    state.value = "";
    email.value = "";
    phone.value = "";
    addUserbtn.style.display = "block";
    updateUserbtn.style.display = "none";
}
function editFun(event, i) {
    // Using existing userDetails modal turn by turn for 2 different buttons.

    // 1. When addUserbtn will be clicked userDetailsmodal will get open with empty input fields
    // So that user can add his details into the database;

    // 2. When updateUserBtn will be clicked userDetailsModal will get open with filled input fields 
    // so that user can make the required changes and simply update his details. 

    addUserbtn.style.display = "none";
    updateUserbtn.style.display = "block";

    editUuid = mData[i].uuid;
    fname.value = mData[i].first_name;
    lname.value = mData[i].last_name;
    street.value = mData[i].street;
    add.value = mData[i].address;
    city.value = mData[i].city;
    state.value = mData[i].state;
    email.value = mData[i].email;
    phone.value = mData[i].phone;
}
// onclick of submit of updateModal updating data into the database
updateUserbtn.addEventListener('click', function (e) {

    userObj = {
        "uuid": editUuid,
        "first_name": fname.value,
        "last_name": lname.value,
        "street": street.value,
        "address": add.value,
        "city": city.value,
        "state": state.value,
        "email": email.value,
        "phone": phone.value
    }
    fetch(`${ServerPort}/update-customer`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userObj)
    })
        .then(response => response.text())
        .then(data => {
            alert("Status : " + data)
        })
        .catch(error => {
            alert("Error : " + error);
            console.error('Error:', error)
        });

    addUserbtn.style.display = "block";
    updateUserbtn.style.display = "none";
    location.reload();
})


// 2nd Phase
//Making api call for getting the token
loginButton.addEventListener('click', async function(e){
    let loginObj = {
        "login_id": loginId.value,
        "password": loginPassword.value
    }
    console.log(loginObj);
    let response =await fetch(`https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp`,{
            method:'POST',
            body: JSON.stringify(loginObj)
        });
    let tokenObj = await response.json();
    // console.log(token);

    let response2 = await fetch(`${ServerPort}/get-customer-list-with-token`,{
        method:'GET',
        headers: {
            'Content-Type': 'application/json',
            'access_token':`${tokenObj.access_token}`
          }
    })
    console.log(response2);
    let anotherData = await response2.json();
    location.reload();

})



