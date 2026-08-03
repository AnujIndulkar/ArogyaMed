import { Link } from "react-router-dom";

export function Footer() {
  return (
    <footer className="bg-ink-900 text-white/70">
      <div className="max-w-7xl mx-auto px-6 py-14 grid grid-cols-2 md:grid-cols-4 gap-10">
        <div className="col-span-2 md:col-span-1">
          <div className="flex items-center gap-2 mb-3">
            <div className="w-8 h-8 rounded-lg bg-gradient-primary flex items-center justify-center text-white font-display font-bold text-sm">
              A
            </div>
            <span className="font-display font-bold text-white">ArogyaMed</span>
          </div>
          <p className="text-sm text-white/50 max-w-xs">
            Healthcare and the medicine supply chain, on one connected
            platform.
          </p>
        </div>

        <div>
          <p className="font-display font-semibold text-white text-sm mb-3">Platform</p>
          <ul className="flex flex-col gap-2 text-sm">
            <li><Link to="/register" className="hover:text-white">Find a doctor</Link></li>
            <li><Link to="/register" className="hover:text-white">Order medicine</Link></li>
            <li><Link to="/ambulance" className="hover:text-white">Book ambulance</Link></li>
          </ul>
        </div>

        <div>
          <p className="font-display font-semibold text-white text-sm mb-3">Company</p>
          <ul className="flex flex-col gap-2 text-sm">
            <li><a href="#" className="hover:text-white">About</a></li>
            <li><a href="#" className="hover:text-white">Careers</a></li>
            <li><a href="#" className="hover:text-white">Contact</a></li>
          </ul>
        </div>

        <div>
          <p className="font-display font-semibold text-white text-sm mb-3">Legal</p>
          <ul className="flex flex-col gap-2 text-sm">
            <li><a href="#" className="hover:text-white">Privacy</a></li>
            <li><a href="#" className="hover:text-white">Terms</a></li>
          </ul>
        </div>
      </div>

      <div className="border-t border-white/10 py-5 text-center text-xs text-white/40">
        © {new Date().getFullYear()} ArogyaMed. All rights reserved.
      </div>
    </footer>
  );
}
