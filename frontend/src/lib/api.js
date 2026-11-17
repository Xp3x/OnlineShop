
import { getClientId } from "./token.js";

const API_BASE = import.meta.env.VITE_API_BASE ?? "http://localhost:8080";

export async function apiFetch(path, options = {}) {
    const p = path.startsWith("/") ? path : `/${path}`;
    const headers = {
        "Content-Type": "application/json",
        "X-Client-Id": getClientId(),
        ...(options.headers || {}),
    };

    const res = await fetch(`${API_BASE}${p}`, { ...options, headers });

    if (!res.ok) {
        let msg = `HTTP ${res.status}`;
        try { msg += `: ${await res.text()}`; } catch {}
        throw new Error(msg);
    }

    if (res.status === 204) return null;

    const ct = res.headers.get("content-type") || "";
    const text = await res.text();
    if (!text) return null;

    if (ct.includes("application/json")) {

        try { return JSON.parse(text); } catch { return null; }
    }
    return text;
}

