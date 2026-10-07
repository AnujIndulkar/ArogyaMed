// Draws a simple illustration of the medicine (strip, bottle, vial or tube)
// based on its name / pack size. Used when a medicine has no photo.

export type MedicineKind = "tablet" | "capsule" | "syrup" | "injection" | "topical";

export function medicineKind(name = "", packSize = ""): MedicineKind {
  const t = `${name} ${packSize}`.toLowerCase();
  if (/(syrup|suspension|bottle|drops|solution for oral)/.test(t)) return "syrup";
  if (/(injection|cartridge|vial|ampoule|infusion)/.test(t)) return "injection";
  if (/(cream|ointment|gel|lotion|tube|spray)/.test(t)) return "topical";
  if (/capsule/.test(t)) return "capsule";
  return "tablet";
}

export function dosageFormLabel(kind: MedicineKind): string {
  return {
    tablet: "Tablet",
    capsule: "Capsule",
    syrup: "Liquid / Syrup",
    injection: "Injection",
    topical: "Topical (cream / gel)",
  }[kind];
}

const PRIMARY = "#6D5EF7";
const PRIMARY_LIGHT = "#E4E0FD";

function Strip({ capsule }: { capsule: boolean }) {
  const cols = [30, 70, 110, 150];
  const rows = [48, 98];
  return (
    <g>
      <rect x="12" y="22" width="176" height="116" rx="12" fill="#EEF0F7" stroke="#D5D8E6" strokeWidth="2" />
      <rect x="12" y="22" width="176" height="14" rx="7" fill={PRIMARY} opacity="0.85" />
      {rows.map((y) =>
        cols.map((x) => (
          <g key={`${x}-${y}`}>
            <rect x={x - 14} y={y - 14} width="36" height="36" rx="18" fill="#FFFFFF" stroke="#D5D8E6" />
            {capsule ? (
              <g transform={`rotate(-35 ${x + 4} ${y + 4})`}>
                <rect x={x - 7} y={y - 1} width="22" height="11" rx="5.5" fill={PRIMARY} />
                <rect x={x + 4} y={y - 1} width="11" height="11" rx="5.5" fill="#FF8A8A" />
              </g>
            ) : (
              <ellipse cx={x + 4} cy={y + 4} rx="11" ry="9" fill="#F7C8D0" stroke="#E8A5B1" />
            )}
          </g>
        ))
      )}
    </g>
  );
}

function Bottle() {
  return (
    <g>
      <rect x="78" y="14" width="44" height="22" rx="5" fill={PRIMARY} />
      <rect x="86" y="34" width="28" height="14" fill="#D5D8E6" />
      <rect x="58" y="46" width="84" height="104" rx="16" fill="#FFE9C7" stroke="#F2C77F" strokeWidth="2" />
      <rect x="58" y="78" width="84" height="46" fill="#FFFFFF" />
      <rect x="68" y="88" width="46" height="6" rx="3" fill={PRIMARY} />
      <rect x="68" y="100" width="32" height="5" rx="2.5" fill="#C9C1FB" />
      <rect x="68" y="110" width="38" height="5" rx="2.5" fill="#C9C1FB" />
    </g>
  );
}

function Vial() {
  return (
    <g>
      <rect x="78" y="12" width="44" height="18" rx="4" fill="#FF8A8A" />
      <rect x="70" y="28" width="60" height="10" rx="3" fill="#C9CCDD" />
      <path d="M76 38 h48 v18 q0 8 8 14 v66 q0 12 -12 12 h-40 q-12 0 -12 -12 v-66 q8 -6 8 -14 z" fill="#E7F4FF" stroke="#9CC9EC" strokeWidth="2" />
      <rect x="72" y="92" width="56" height="36" rx="4" fill="#FFFFFF" />
      <rect x="80" y="100" width="34" height="6" rx="3" fill={PRIMARY} />
      <rect x="80" y="112" width="26" height="5" rx="2.5" fill="#C9C1FB" />
      <rect x="72" y="70" width="56" height="20" fill="#BFE1FA" opacity="0.7" />
    </g>
  );
}

function Tube() {
  return (
    <g>
      <rect x="86" y="10" width="28" height="22" rx="4" fill={PRIMARY} />
      <path d="M72 32 h56 l-6 112 h-44 z" fill="#FFFFFF" stroke="#D5D8E6" strokeWidth="2" />
      <path d="M66 144 h68 v8 h-68 z" fill="#D5D8E6" />
      <rect x="82" y="60" width="36" height="8" rx="4" fill={PRIMARY} />
      <rect x="86" y="76" width="28" height="6" rx="3" fill="#C9C1FB" />
      <rect x="86" y="88" width="22" height="6" rx="3" fill="#C9C1FB" />
    </g>
  );
}

export function MedicineVisual({
  name,
  packSize,
  className = "",
}: {
  name?: string;
  packSize?: string;
  className?: string;
}) {
  const kind = medicineKind(name, packSize);
  return (
    <div
      className={`flex items-center justify-center bg-gradient-to-br from-primary-50 to-white ${className}`}
      aria-label={`${dosageFormLabel(kind)} illustration`}
    >
      <svg viewBox="0 0 200 160" className="w-full h-full p-3" role="img">
        <circle cx="100" cy="80" r="70" fill={PRIMARY_LIGHT} opacity="0.45" />
        {kind === "tablet" && <Strip capsule={false} />}
        {kind === "capsule" && <Strip capsule />}
        {kind === "syrup" && <Bottle />}
        {kind === "injection" && <Vial />}
        {kind === "topical" && <Tube />}
      </svg>
    </div>
  );
}