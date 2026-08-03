import { useState, type ReactNode } from "react";
import { Search as SearchIcon } from "lucide-react";
import { TableSkeleton, EmptyState } from "./EmptyState";
import type { LucideIcon } from "lucide-react";

export interface Column<T> {
  header: string;
  accessor: (row: T) => ReactNode;
  className?: string;
}

interface DataTableProps<T> {
  data: T[] | undefined;
  columns: Column<T>[];
  isLoading?: boolean;
  keyField: (row: T) => string | number;
  searchable?: boolean;
  searchPlaceholder?: string;
  onSearch?: (query: string) => void;
  emptyIcon: LucideIcon;
  emptyTitle: string;
  emptyDescription?: string;
  headerActions?: ReactNode;
}

export function DataTable<T>({
  data,
  columns,
  isLoading,
  keyField,
  searchable = true,
  searchPlaceholder = "Search...",
  onSearch,
  emptyIcon,
  emptyTitle,
  emptyDescription,
  headerActions,
}: DataTableProps<T>) {
  const [query, setQuery] = useState("");

  const handleSearchChange = (value: string) => {
    setQuery(value);
    onSearch?.(value);
  };

  return (
    <div>
      {(searchable || headerActions) && (
        <div className="flex items-center justify-between gap-3 mb-5">
          {searchable && (
            <div className="flex items-center gap-2 bg-surface rounded-xl px-3.5 py-2.5 border border-surface-border w-full max-w-xs">
              <SearchIcon size={16} className="text-ink-300 shrink-0" />
              <input
                value={query}
                onChange={(e) => handleSearchChange(e.target.value)}
                placeholder={searchPlaceholder}
                className="bg-transparent outline-none text-sm placeholder:text-ink-300 w-full"
              />
            </div>
          )}
          {headerActions}
        </div>
      )}

      {isLoading ? (
        <TableSkeleton rows={6} />
      ) : !data || data.length === 0 ? (
        <EmptyState icon={emptyIcon} title={emptyTitle} description={emptyDescription} />
      ) : (
        <div className="overflow-x-auto -mx-1">
          <table className="w-full text-sm">
            <thead>
              <tr className="text-left text-ink-500 border-b border-surface-border">
                {columns.map((col, i) => (
                  <th key={i} className={`pb-3 px-1 font-medium whitespace-nowrap ${col.className ?? ""}`}>
                    {col.header}
                  </th>
                ))}
              </tr>
            </thead>
            <tbody>
              {data.map((row) => (
                <tr key={keyField(row)} className="border-b border-surface-border last:border-0 hover:bg-surface/60">
                  {columns.map((col, i) => (
                    <td key={i} className={`py-3.5 px-1 text-ink-700 ${col.className ?? ""}`}>
                      {col.accessor(row)}
                    </td>
                  ))}
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
