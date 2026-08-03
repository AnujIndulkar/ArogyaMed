# ArogyaMed Frontend

React 19 + TypeScript + Vite + Tailwind CSS frontend for the ArogyaMed healthcare platform.

## Setup

```bash
npm install
npm run dev
```

App runs at `http://localhost:5173`.

## Before you run it

1. Your Spring Boot backend must be running at `http://localhost:8080` (see `.env` — `VITE_API_BASE_URL`).
2. Your backend needs the CORS config (`CorsConfig.java` + `SecurityConfig.java` update) allowing origin `http://localhost:5173` — see the backend conversation for that file.
3. Jakarta Validation must be enabled on your DTOs (Phase 3) or registration/login may behave unexpectedly on bad input.

## What's built

- **Auth**: Login, Register, JWT stored in localStorage, role-based redirect, protected routes
- **Landing page** (`/`): hero, medicine search, categories, top doctors, ambulance CTA, articles, testimonials, floating SOS button
- **7 role dashboards**: Patient, Doctor, Pharmacist, Wholesaler, Company, Delivery Partner, Admin — each with distinct stats and charts (Recharts)
- **Ambulance & SOS** (`/ambulance`): live Leaflet map, nearby ambulances, booking, emergency contacts
- **Floating SOS button**: long-press (3s) to confirm, emergency contact + booking modal
- **AI Symptom Checker** (`/patient/symptom-checker`): wired to your backend's `/api/ai/symptom-checker` endpoint

## What's stubbed (placeholder pages)

Every dashboard sidebar links to feature pages (Inventory, Orders, Prescriptions, KYC, Audit Log, etc.) that currently render a "coming next" placeholder. The routing, layout, and navigation are fully wired — only the page content needs to be filled in module by module.

## Folder structure

```
src/
  api/            → axios instance with JWT interceptor
  components/
    common/       → Button, Input, Card, StatCard, Badge, AuthLayout, etc.
    layout/       → Sidebar, Navbar, DashboardLayout, Footer, FloatingSOSButton
  pages/          → one folder per role + auth + ambulance
  contexts/       → AuthContext
  hooks/          → useAuth
  services/       → authService, aiService, domainServices (generic CRUD)
  routes/         → AppRoutes, ProtectedRoute
  theme/          → JS-side design tokens (for charts)
  constants/      → config, roles, navigation
  types/          → TypeScript interfaces matching backend DTOs
```

## Design system

- Primary `#6D5EF7`, Secondary `#8B5CF6`, Accent `#FF6B6B`
- Fonts: Plus Jakarta Sans (headings), Inter (body), JetBrains Mono (stats)
- All tokens defined in `tailwind.config.js` and `src/theme/tokens.ts`
