// -- == [[ CONSTANTS ]] == -- \\

const QUICK_STATS_ENDPOINT = "/api/stats";
const GET_TRANSACTION_LIST_ENDPOINT = "/api/transaction";
const CREATE_TRANSACTION_ENDPOINT = "/api/transaction";
const EDIT_TRANSACTION_ENDPOINT = "/api/transaction";
const DELETE_TRANSACTION_ENDPOINT = "/api/transaction";



// -- == [[ CREATE ]] == -- \\

const createTransaction = async (newTransactionInfo) => {

    if (!newTransactionInfo) {
        throw new Error("newTransactionInfo was undefined!");
    };

    // Destructure newTransactionInfo

    const {
        title,
        description,
        date,
        amount,
        type
    } = newTransactionInfo;


    // Create request with newTransactionInfo

    const BODY = JSON.stringify({
        title,
        description,
        date,
        amount,
        type
    });

    const OPTIONS = {

        method: "POST",
        body: BODY,

        headers: {
            "content-type": "application/json"
        },

    }


    // Send request and clear form

    const response = await fetch(CREATE_TRANSACTION_ENDPOINT, OPTIONS);


    // Process response

    if (!response.ok) {

        // If server sent back invalid response, handle

        console.warn(response);
        displayCreateTransactionFormError("There was an error creating transaction");

        return;

    }

    const json = await response.json();

    if (!json) {
        console.warn("Could not convert response to JSON object");
        return;
    }

    return json;

}



// -- == [[ READ ]] == -- \\

const getQuickStats = async () => {

    // Sends request for quick stats

    const response = await fetch(QUICK_STATS_ENDPOINT);


    // Handles response

    if (!response.ok) {

        // If server sent back invalid response, handle

        console.warn(response);
        displayCreateTransactionFormError("There was an error getting quick stats");

        return;

    }

    const json = await response.json();

    if (!json) {
        console.warn("Could not convert get quick stats response to JSON object");
        return;
    }


    // Returns quick stats json

    return json;

}

const getTransactionList = async () => {

    // Sends request for quick stats

    const response = await fetch(GET_TRANSACTION_LIST_ENDPOINT);


    // Handles response

    if (!response.ok) {

        // If server sent back invalid response, handle

        console.warn(response);
        displayCreateTransactionFormError("There was an error getting transaction list");

        return;

    }

    const json = await response.json();

    if (!json) {
        console.warn("Could not convert get transaction list response to JSON object");
        return;
    }


    // Returns quick stats json

    return json;

}


// -- == [[ UPDATE ]] == -- \\

const editTransaction = async (transactionID, newTransactionInfo) => {

    if (!newTransactionInfo) {
        throw new Error("newTransactionInfo was undefined!");
    };

    // Destructure newTransactionInfo

    const {
        id,
        title,
        description,
        date,
        amount,
        type
    } = newTransactionInfo;


    // Create request with newTransactionInfo

    const BODY = JSON.stringify({
        id,
        title,
        description,
        date,
        amount,
        type
    });

    const OPTIONS = {

        method: "PUT",
        body: BODY,

        headers: {
            "content-type": "application/json"
        },

    }


    // Send request and clear form

    const response = await fetch(`${EDIT_TRANSACTION_ENDPOINT}/${transactionID}`, OPTIONS);


    // Process response

    if (!response.ok) {

        // If server sent back invalid response, handle

        console.warn(response);
        displayEditTransactionFormError("There was an error editing transaction");

        return;

    }

    const json = await response.json();

    if (!json) {
        console.warn("Could not convert response to JSON object");
        return;
    }

    return json;

}


// -- == [[ DELETE ]] == -- \\

const deleteTransaction = async (transactionID) => {

    if (!transactionID) {
        throw new Error("transactionID was undefined!");
    };


    // Create request with transactionID

    const OPTIONS = {

        method: "DELETE",

        headers: {
            "content-type": "application/json"
        },

    }


    // Send request to delete transaction

    const response = await fetch(`${DELETE_TRANSACTION_ENDPOINT}/${transactionID}`, OPTIONS);


    // Processes response

    if (!response.ok) {

        // If server sent back invalid response, handle

        console.warn(response);
        displayCreateTransactionFormError("There was an error creating transaction");

        return;

    }

    const json = await response.json();

    if (!json) {
        console.warn("Could not convert response to JSON object");
        return;
    }

    return json;

}