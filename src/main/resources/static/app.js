const resultText = document.getElementById("resultText");

function show(msg) {
    if (resultText) resultText.textContent = msg;
}

async function postJson(url, data) {
    const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });

    const text = await res.text();
    let json = null;
    try { json = JSON.parse(text); } catch {}
    return { ok: res.ok, status: res.status, text, json };
}

document.getElementById("btnSignup")?.addEventListener("click", async () => {
    const payload = {
        name: su_name.value,
        surname: su_surname.value,
        email: su_email.value,
        password: su_password.value,
        phoneNumber: su_phone.value,
        address: su_address.value
    };

    const r = await postJson("/api/auth/signup", payload);
    show(r.text);
});

document.getElementById("btnLogin")?.addEventListener("click", async () => {
    const payload = {
        email: li_email.value,
        password: li_password.value
    };

    const r = await postJson("/api/auth/login", payload);

    if (r.ok) {
        localStorage.setItem("token", r.json.token);
        window.location.href = "/dashboard.html";
    } else {
        show(r.text);
    }
});
