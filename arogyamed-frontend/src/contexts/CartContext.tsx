import type { Medicine } from "@/types/common.types";
import { createContext, useState, type ReactNode } from "react";

export interface CartItem {
  medicineId: number;
  medicineName: string;
  price: number;
  quantity: number;
}

interface CartContextType {
  items: CartItem[];
  addToCart: (medicine: Medicine, quantity?: number) => void;
  removeFromCart: (medicineId: number) => void;
  updateQuantity: (medicineId: number, quantity: number) => void;
  clearCart: () => void;
  totalAmount: number;
  totalItems: number;
}

export const CartContext = createContext<CartContextType | undefined>(undefined);
console.log("PROVIDER CONTEXT:", CartContext);

export function CartProvider({ children }: { children: ReactNode }) {
  console.log("🔥 CartProvider Mounted");

  const [items, setItems] = useState<CartItem[]>([]);
  const addToCart = (medicine: Medicine, quantity = 1) => {
    setItems((prev) => {
      const existing = prev.find((i) => i.medicineId === medicine.id);

      if (existing) {
        return prev.map((i) =>
          i.medicineId === medicine.id ? { ...i, quantity: i.quantity + quantity } : i
        );
      }

      return [
        ...prev,
        { medicineId: medicine.id, medicineName: medicine.medicineName, price: medicine.price, quantity },
      ];
    });
  };

  const removeFromCart = (medicineId: number) => {
    setItems((prev) => prev.filter((i) => i.medicineId !== medicineId));
  };

  const updateQuantity = (medicineId: number, quantity: number) => {
    if (quantity <= 0) {
      removeFromCart(medicineId);
      return;
    }

    setItems((prev) => prev.map((i) => (i.medicineId === medicineId ? { ...i, quantity } : i)));
  };

  const clearCart = () => setItems([]);

  const totalAmount = items.reduce((sum, i) => sum + i.price * i.quantity, 0);
  const totalItems = items.reduce((sum, i) => sum + i.quantity, 0);

  return (
    <CartContext.Provider
      value={{ items, addToCart, removeFromCart, updateQuantity, clearCart, totalAmount, totalItems }}
    >
      {children}
    </CartContext.Provider>
  );
}