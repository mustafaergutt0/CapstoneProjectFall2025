const resultText = document.getElementById("resultText");

function show(msg) {
    resultText.textContent = msg;
}

async function postJson(url, data) {
    const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
    const text = await res.text();
    return { ok: res.ok, status: res.status, text };
}

document.getElementById("btnSignup").addEventListener("click", async () => {
    const payload = {
        name: document.getElementById("su_name").value,
        surname: document.getElementById("su_surname").value,
        email: document.getElementById("su_email").value,
        password: document.getElementById("su_password").value,
        phoneNumber: document.getElementById("su_phone").value,
        // DTO'da alan adı "adress" ise bunu böyle bırak
        address: document.getElementById("su_address").value
    };

    const r = await postJson("/api/auth/signup", payload);
    show(`SIGNUP -> status=${r.status}\n${r.text}`);
});

document.getElementById("btnLogin").addEventListener("click", async () => {
    const payload = {
        email: document.getElementById("li_email").value,
        password: document.getElementById("li_password").value
    };

    const r = await postJson("/api/auth/login", payload);
    show(`LOGIN -> status=${r.status}\n${r.text}`);
});
