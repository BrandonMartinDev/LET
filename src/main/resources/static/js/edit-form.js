const hideEditTransactionFormMessages = () => {

    // Get error and success elements from document

    const errorWrapper = document.getElementById("edit-error-wrapper");
    const successWrapper = document.getElementById("edit-success-wrapper");


    // Clear inner HTML

    errorWrapper.innerHTML = "";
    successWrapper.innerHTML = "";

}

const displayEditTransactionFormError = (errMsg) => {

    // Get error wrapper element from document and set inner html

    const errorWrapper = document.getElementById("edit-error-wrapper");
    errorWrapper.innerHTML = `<div class="error-message">${errMsg}</div>`;

}

const displayEditTransactionFormSuccess = (successMsg) => {

    // Get success wrapper element from document and set inner html

    const successWrapper = document.getElementById("edit-success-wrapper");
    successWrapper.innerHTML = `<div class="success-message">${successMsg}</div>`;

    setTimeout(hideEditTransactionFormMessages, 3000);

}



const populateEditTransactionForm = (transactionInfo) => {

    // Get elements from document

    const id = document.getElementById("edit-id");

    const title = document.getElementById('edit-title');
    const description = document.getElementById('edit-description');
    const date = document.getElementById('edit-date');
    const amount = document.getElementById('edit-amount');

    const donationRadio = document.getElementById('edit-donation');
    const expenseRadio = document.getElementById('edit-expense');


    // Set values to transactionInfo

    id.innerText = transactionInfo.id;

    title.value = transactionInfo.title;
    description.value = transactionInfo.description;
    date.value = transactionInfo.date;
    amount.value = transactionInfo.amount;

    donationRadio.checked = (transactionInfo.type === "DONATION");
    expenseRadio.checked = (transactionInfo.type === "EXPENSE");

}

const clearEditTransactionForm = () => {

    // Get elements from document

    const id = document.getElementById("edit-id");

    const title = document.getElementById('edit-title');
    const description = document.getElementById('edit-description');
    const date = document.getElementById('edit-date');
    const amount = document.getElementById('edit-amount');

    const donationRadio = document.getElementById('edit-donation');
    const expenseRadio = document.getElementById('edit-expense');


    // Clear values

    id.innerText = "xxxxxxx";

    title.value = "";
    description.value = "";
    date.value = "";
    amount.value = "";

    donationRadio.checked = false;
    expenseRadio.checked = false;

}

const submitEditTransaction = async (e) => {

    // Prevent reloading and hide messages

    e.preventDefault();
    hideEditTransactionFormMessages();


    // Get form data

    const id = document.getElementById("edit-id").innerText;

    const title = document.getElementById('edit-title').value;
    const description = document.getElementById('edit-description').value;
    const date = document.getElementById('edit-date').value;
    const amount = document.getElementById('edit-amount').value;

    const donationRadio = document.getElementById('edit-donation');
    const expenseRadio = document.getElementById('edit-expense');
    const type = donationRadio.checked ? "DONATION" : "EXPENSE";



    // Client-side validates form data

    if (id.length !== 7) {
        displayEditTransactionFormError("ID is invalid!");
        return;
    }

    if (title.length < 1) {
        displayEditTransactionFormError("Title is required!");
        return;
    } else if (title.length > 150) {
        displayEditTransactionFormError("Title is greater than 150 characters!");
        return;
    }

    if (description.length < 1) {
        displayEditTransactionFormError("Description is required!");
        return;
    } else if (description.length > 1000) {
        displayEditTransactionFormError("Description is greater than 150 characters!");
        return;
    }

    if (donationRadio.checked === false && expenseRadio.checked === false) {
        displayEditTransactionFormError("Transaction type is required!");
        return;
    }

    if (isNaN(amount) || amount < 0) {
        displayEditTransactionFormError("Amount is required");
        return;
    }

    if (isNaN(Date.parse(date))) {
        displayEditTransactionFormError("Date is required");
        return;
    }


    // Edits new transaction

    const newTransactionInfo = {
        id,
        title,
        description,
        date,
        amount,
        type
    }

    const editedTransaction = await editTransaction(id, newTransactionInfo);
    if (!editedTransaction) return;


    // Update UI

    console.log(`Successfully edited transaction: ${editedTransaction.id}`);
    displayEditTransactionFormSuccess(`Successfully edited transaction '${editedTransaction.id}'`)
    refreshData();
    clearEditTransactionForm();

}