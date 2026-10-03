import { createContext, useState, useEffect, type ReactNode } from "react";
import type { AuthUser, LoginResponse } from "@/types/auth.types";
import { TOKEN_KEY, USER_KEY } from "@/constants/config";

interface AuthContextType {
  user: AuthUser | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (response: LoginResponse) => void;
  logout: () => void;
  updateUser: (partial: Partial<AuthUser>) => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthUser | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const storedUser = localStorage.getItem(USER_KEY);
    const token = localStorage.getItem(TOKEN_KEY);

    if (storedUser && token) {
      setUser(JSON.parse(storedUser));
    }

    setIsLoading(false);
  }, []);

  const login = (response: LoginResponse) => {
    const authUser: AuthUser = {
      userId: response.userId,
      fullName: response.fullName,
      email: response.email,
      role: response.role,
    };

    localStorage.setItem(TOKEN_KEY, response.token);
    localStorage.setItem(USER_KEY, JSON.stringify(authUser));

    setUser(authUser);
  };

  const logout = () => {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    setUser(null);
  };

  const updateUser = (partial: Partial<AuthUser>) => {
    setUser((prev) => {
      if (!prev) return prev;
      const next = { ...prev, ...partial };
      localStorage.setItem(USER_KEY, JSON.stringify(next));
      return next;
    });
  };

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated: !!user, isLoading, login, logout, updateUser }}
    >
      {children}
    </AuthContext.Provider>
  );
}