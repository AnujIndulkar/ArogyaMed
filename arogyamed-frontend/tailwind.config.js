/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "#6D5EF7",
          50: "#F1EFFE",
          100: "#E4E0FD",
          200: "#C9C1FB",
          300: "#AEA2F9",
          400: "#9383F8",
          500: "#6D5EF7",
          600: "#4B39F4",
          700: "#3420D6",
          800: "#2818A3",
          900: "#1C1170",
        },
        secondary: {
          DEFAULT: "#8B5CF6",
          50: "#F3EEFE",
          100: "#E7DDFD",
          500: "#8B5CF6",
          600: "#7C3AED",
          700: "#6D28D9",
        },
        accent: {
          DEFAULT: "#FF6B6B",
          50: "#FFF0F0",
          100: "#FFE1E1",
          500: "#FF6B6B",
          600: "#FF4747",
        },
        success: {
          DEFAULT: "#22C55E",
          50: "#F0FDF4",
          500: "#22C55E",
          600: "#16A34A",
        },
        warning: {
          DEFAULT: "#F59E0B",
          50: "#FFFBEB",
          500: "#F59E0B",
          600: "#D97706",
        },
        surface: {
          DEFAULT: "#F8F9FC",
          card: "#FFFFFF",
          border: "#EDEEF5",
        },
        ink: {
          900: "#14152B",
          700: "#3A3B54",
          500: "#6B6C82",
          300: "#A5A6B8",
        },
      },
      fontFamily: {
        display: ["'Plus Jakarta Sans'", "sans-serif"],
        body: ["'Inter'", "sans-serif"],
        mono: ["'JetBrains Mono'", "monospace"],
      },
      borderRadius: {
        xl: "1rem",
        "2xl": "1.5rem",
        "3xl": "2rem",
      },
      boxShadow: {
        soft: "0 4px 24px -4px rgba(20, 21, 43, 0.06)",
        card: "0 2px 12px -2px rgba(20, 21, 43, 0.08)",
        glow: "0 0 0 1px rgba(109, 94, 247, 0.1), 0 8px 24px -4px rgba(109, 94, 247, 0.25)",
        "glow-accent": "0 8px 24px -4px rgba(255, 107, 107, 0.4)",
      },
      backgroundImage: {
        "gradient-primary": "linear-gradient(135deg, #6D5EF7 0%, #8B5CF6 100%)",
        "gradient-accent": "linear-gradient(135deg, #FF6B6B 0%, #FF4747 100%)",
        "gradient-mesh":
          "radial-gradient(at 20% 0%, rgba(109,94,247,0.12) 0px, transparent 50%), radial-gradient(at 80% 0%, rgba(255,107,107,0.08) 0px, transparent 50%)",
      },
      keyframes: {
        "pulse-ring": {
          "0%": { transform: "scale(0.9)", opacity: "0.7" },
          "70%": { transform: "scale(1.5)", opacity: "0" },
          "100%": { transform: "scale(1.5)", opacity: "0" },
        },
        "fade-up": {
          "0%": { opacity: "0", transform: "translateY(12px)" },
          "100%": { opacity: "1", transform: "translateY(0)" },
        },
        shimmer: {
          "0%": { backgroundPosition: "-1000px 0" },
          "100%": { backgroundPosition: "1000px 0" },
        },
      },
      animation: {
        "pulse-ring": "pulse-ring 2s cubic-bezier(0.4, 0, 0.6, 1) infinite",
        "fade-up": "fade-up 0.5s ease-out forwards",
        shimmer: "shimmer 2s infinite linear",
      },
    },
  },
  plugins: [],
};
