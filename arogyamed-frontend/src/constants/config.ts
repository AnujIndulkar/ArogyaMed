export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

// Strip trailing /api so we can build full URLs for uploaded files (images, docs)
export const BACKEND_ORIGIN = API_BASE_URL.replace(/\/api\/?$/, "");

export const TOKEN_KEY = "arogyamed_token";
export const USER_KEY = "arogyamed_user";