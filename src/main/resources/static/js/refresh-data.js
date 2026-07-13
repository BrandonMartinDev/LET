// -- == [[ QUICK STATS ]] == -- \\

const refreshQuickStats = async () => {

    // Gets quick stats

    const quickStats = await getQuickStats();
    if (!quickStats) throw new Error("Could not get quick stats");

    const { networth, donations, expenses } = quickStats;


    // Gets quick stats elements

    const networthElement = document.getElementById("networth-display");
    const donationsElement = document.getElementById("donations-display");
    const expensesElement = document.getElementById("expenses-display");


    // Set quick stats elements text to quick stats

    networthElement.innerText = networth.toFixed(2);
    donationsElement.innerText = donations.toFixed(2);
    expensesElement.innerText = expenses.toFixed(2);

}



// -- == [[ TRANSACTION LIST ]] == -- \\

let backgroundShading = "light";

const addTransactionToTable = (transactionInfo) => {

    // Get transaction list table body in document

    const tbody = document.querySelector("tbody");


    // Create table row

    const tableRow = document.createElement("tr");

    tableRow.setAttribute("class", backgroundShading);
    backgroundShading = (backgroundShading === "dark" ? "light" : "dark");


    // Create td html elements

    const idElem = document.createElement("td");
    const titleElem = document.createElement("td");
    const descriptionElem = document.createElement("td");
    const typeElem = document.createElement("td");
    const dateElem = document.createElement("td");
    const amountElem = document.createElement("td");

    const deleteButtonWrapper = document.createElement("td");
    const deleteButton = document.createElement("button");
    const deleteButtonIcon = document.createElement("img");


    // Change inner text and attributes for each element

    idElem.innerText = transactionInfo.id;
    idElem.setAttribute("class", "id");
    idElem.setAttribute("title", transactionInfo.id);

    titleElem.innerText = transactionInfo.title;
    titleElem.setAttribute("class", "title");
    titleElem.setAttribute("title", transactionInfo.title);

    descriptionElem.innerText = transactionInfo.description;
    descriptionElem.setAttribute("class", "description");
    descriptionElem.setAttribute("title", transactionInfo.description);

    typeElem.innerText = transactionInfo.type;
    typeElem.setAttribute("class", "type");
    typeElem.setAttribute("title", transactionInfo.type);

    const dateFromTInfo = new Date(transactionInfo.date);
    const dateString = (`${dateFromTInfo.getUTCMonth() + 1}/${dateFromTInfo.getUTCDate()}/${dateFromTInfo.getUTCFullYear()}`);
    dateElem.innerText = dateString;
    dateElem.setAttribute("class", "date");
    dateElem.setAttribute("title", dateString);

    const amountString = (`${transactionInfo.type === "EXPENSE" ? "-" : "+"}$${transactionInfo.amount.toFixed(2)}`)
    amountElem.innerText = amountString;
    amountElem.setAttribute("class", "amount");
    amountElem.setAttribute("title", amountString);

    deleteButtonWrapper.setAttribute("class", "delete-button-wrapper");

    deleteButton.setAttribute("class", "delete-button")

    deleteButtonIcon.setAttribute("src", "assets/trash-icon.png");
    deleteButtonIcon.setAttribute("title", "delete transaction");


    // Add each element to respective parents, then to document

    deleteButton.appendChild(deleteButtonIcon);
    deleteButtonWrapper.appendChild(deleteButton);

    tableRow.appendChild(idElem);
    tableRow.appendChild(titleElem);
    tableRow.appendChild(descriptionElem);
    tableRow.appendChild(typeElem);
    tableRow.appendChild(dateElem);
    tableRow.appendChild(amountElem);
    tableRow.appendChild(deleteButtonWrapper);

    tbody.appendChild(tableRow);


    // Handle delete button events

    let debounce = false; // Debounce for table row click and delete button click

    deleteButtonIcon.onmouseover = () => {
        deleteButtonIcon.setAttribute("src", "assets/trash-icon_hover.png");
    }

    deleteButtonIcon.onmouseleave = () => {
        deleteButtonIcon.setAttribute("src", "assets/trash-icon.png");
    }

    deleteButtonIcon.onclick = async () => {

        debounce = true;

        console.log("DELETING:", transactionInfo.id);

        const successfullyDeleted = await deleteTransaction(transactionInfo.id);

        if (!successfullyDeleted) {
            alert("Did not delete successfully");
            return;
        }

        await refreshData();
        alert("Deleted successfully");

        clearEditTransactionForm();

        debounce = false;

    }


    // Handle table row click event

    tableRow.onclick = () => {

        if (debounce) return;

        populateEditTransactionForm(transactionInfo);

    }

}

const refreshTransactionList = async () => {

    // Gets transaction list

    const transactionList = await getTransactionList();
    if (!transactionList) throw new Error("Could not get transaction list");


    // Clears transaction list table

    const tbody = document.querySelector("tbody");
    tbody.innerHTML = "";


    // Adds each transaction in list to table

    transactionList.forEach((transaction) => addTransactionToTable(transaction));

}



// -- == [[ GLOBAL ]] == -- \\

const refreshData = async () => {

    await refreshQuickStats();
    await refreshTransactionList();

}

window.onload = refreshData;