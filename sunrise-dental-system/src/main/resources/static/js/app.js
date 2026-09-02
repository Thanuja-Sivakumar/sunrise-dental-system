// Shared helpers used by every page. All calls go to the REST web-service
// layer exposed by the Spring Boot backend (see the controller package),
// which is what makes this a genuine distributed (client/server) app.

const API_BASE = '/api';

function authHeader() {
    // Sessions are cookie-based once logged in, but we also keep basic auth
    // credentials in sessionStorage as a fallback so a page refresh does
    // not immediately lose the "logged in" UX in this simple demo client.
    const creds = sessionStorage.getItem('creds');
    if (!creds) return {};
    return { 'Authorization': 'Basic ' + creds };
}

async function apiFetch(path, options = {}) {
    const response = await fetch(API_BASE + path, {
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...authHeader(),
            ...(options.headers || {})
        }
    });

    if (response.status === 401 || response.status === 403) {
        window.location.href = '/login.html';
        throw new Error('Not authenticated');
    }

    const data = await response.json().catch(() => ({}));
    if (!response.ok) {
        throw new Error(data.message || 'Request failed');
    }
    return data;
}

function requireLogin() {
    if (!sessionStorage.getItem('creds')) {
        window.location.href = '/login.html';
    }
}

function showMessage(elementId, text, type) {
    const el = document.getElementById(elementId);
    el.textContent = text;
    el.className = 'message ' + type;
    el.style.display = 'block';
}

function logout() {
    sessionStorage.removeItem('creds');
    sessionStorage.removeItem('fullName');
    window.location.href = '/login.html';
}
