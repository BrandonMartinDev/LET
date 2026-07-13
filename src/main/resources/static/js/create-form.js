const hideCreateTransactionFormMessages = () => {

    // Get error and success elements from document

    const errorWrapper = document.getElementById("create-error-wrapper");
    const successWrapper = document.getElementById("create-success-wrapper");


    // Clear inner HTML

    errorWrapper.innerHTML = "";
    successWrapper.innerHTML = "";

}

const displayCreateTransactionFormError = (errMsg) => {

    // Get error wrapper element from document and set inner html

    const errorWrapper = document.getElementById("create-error-wrapper");
    errorWrapper.innerHTML = `<div class="error-message">${errMsg}</div>`;

}

const displayCreateTransactionFormSuccess = (successMsg) => {

    // Get success wrapper element from document and set inner html

    const successWrapper = document.getElementById("create-success-wrapper");
    successWrapper.innerHTML = `<div class="success-message">${successMsg}</div>`;

    setTimeout(hideCreateTransactionFormMessages, 3000);

}

const clearCreateTransactionForm = () => {

    // Get elements from document

    const title = document.getElementById('create-title');
    const description = document.getElementById('create-description');
    const date = document.getElementById('create-date');
    const amount = document.getElementById('create-amount');

    const donationRadio = document.getElementById('create-donation');
    const expenseRadio = document.getElementById('create-expense');


    // Clear values

    title.value = "";
    description.value = "";
    date.value = "";
    amount.value = "";

    donationRadio.checked = false;
    expenseRadio.checked = false;

}

const submitCreateTransaction = async (e) => {

    // Prevent reloading and hide messages

    e.preventDefault();
    hideCreateTransactionFormMessages();


    // Get form data

    const title = document.getElementById('create-title').value;
    const description = document.getElementById('create-description').value;
    const date = document.getElementById('create-date').value;
    const amount = document.getElementById('create-amount').value;

    const donationRadio = document.getElementById('create-donation');
    const expenseRadio = document.getElementById('create-expense');
    const type = donationRadio.checked ? "DONATION" : "EXPENSE";



    // Client-side validates form data

    if (title.length < 1) {
        displayCreateTransactionFormError("Title is required!");
        return;
    } else if (title.length > 150) {
        displayCreateTransactionFormError("Title is greater than 150 characters!");
        return;
    }

    if (description.length < 1) {
        displayCreateTransactionFormError("Description is required!");
        return;
    } else if (description.length > 1000) {
        displayCreateTransactionFormError("Description is greater than 150 characters!");
        return;
    }

    if (donationRadio.checked === false && expenseRadio.checked === false) {
        displayCreateTransactionFormError("Transaction type is required!");
        return;
    }

    if (isNaN(amount) || amount < 0) {
        displayCreateTransactionFormError("Amount is required");
        return;
    }

    if (isNaN(Date.parse(date))) {
        displayCreateTransactionFormError("Date is required");
        return;
    }


    // Creates new transaction

    const newTransactionInfo = {
        title,
        description,
        date,
        amount,
        type
    }

    const createdTransaction = await createTransaction(newTransactionInfo);
    if (!createdTransaction) return;


    // Update UI

    console.log(`Successfully created new transaction: ${createdTransaction.id}`);
    displayCreateTransactionFormSuccess(`Successfully created new transaction '${createdTransaction.id}'`)
    refreshData();
    clearCreateTransactionForm();

}