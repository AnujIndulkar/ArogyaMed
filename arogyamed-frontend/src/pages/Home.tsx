import { motion } from "framer-motion";
import { Link } from "react-router-dom";
import {
  Search,
  MapPin,
  Star,
  Pill,
  Stethoscope,
  Ambulance,
  Heart,
  Thermometer,
  Bone,
  Baby,
  Eye,
  ArrowRight,
} from "lucide-react";
import { Footer } from "@/components/layout/Footer";
import { FloatingSOSButton } from "@/components/layout/FloatingSOSButton";
import { Card } from "@/components/common/Card";

const categories = [
  { label: "Cardiac Care", icon: Heart },
  { label: "Fever & Cold", icon: Thermometer },
  { label: "Orthopedic", icon: Bone },
  { label: "Child Care", icon: Baby },
  { label: "Eye Care", icon: Eye },
  { label: "General", icon: Pill },
];

const doctors = [
  { name: "Dr. Ananya Rao", spec: "Cardiologist", exp: "12 yrs", rating: 4.9 },
  { name: "Dr. Rohan Mehta", spec: "Orthopedic", exp: "9 yrs", rating: 4.8 },
  { name: "Dr. Kavya Nair", spec: "Pediatrician", exp: "7 yrs", rating: 4.9 },
  { name: "Dr. Arjun Desai", spec: "Dermatologist", exp: "11 yrs", rating: 4.7 },
];

const articles = [
  {
    title: "Understanding your blood pressure numbers",
    tag: "Cardiac Health",
  },
  {
    title: "When a fever needs a doctor, not just rest",
    tag: "General Health",
  },
  {
    title: "Storing medicines safely at home",
    tag: "Medicine Safety",
  },
];

const testimonials = [
  {
    quote:
      "Ordered my mother's monthly medicines in two minutes. The reminder feature means we've never missed a refill since.",
    name: "Priya S.",
    role: "Patient",
  },
  {
    quote:
      "The ambulance booking flow is the fastest I've used — live location and ETA gave us real peace of mind.",
    name: "Karan M.",
    role: "Patient",
  },
  {
    quote:
      "As a pharmacist, the inventory and low-stock alerts have cut our restocking errors close to zero.",
    name: "Meera J.",
    role: "Pharmacist Partner",
  },
];

export default function Home() {
  return (
    <div className="bg-surface">
      {/* Navbar */}
      <header className="glass sticky top-0 z-30 h-16 flex items-center justify-between px-6">
        <div className="flex items-center gap-2">
          <div className="w-8 h-8 rounded-lg bg-gradient-primary flex items-center justify-center text-white font-display font-bold text-sm">
            A
          </div>
          <span className="font-display font-bold text-ink-900">ArogyaMed</span>
        </div>

        <div className="flex items-center gap-3">
          <Link to="/login" className="btn-secondary text-sm px-4 py-2">
            Sign in
          </Link>
          <Link to="/register" className="btn-primary text-sm px-4 py-2">
            Get started
          </Link>
        </div>
      </header>

      {/* Hero */}
      <section className="relative overflow-hidden">
        <div className="absolute inset-0 bg-gradient-mesh" />
        <div className="max-w-7xl mx-auto px-6 pt-20 pb-16 relative z-10">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
            className="max-w-2xl"
          >
            <span className="inline-flex items-center gap-1.5 bg-primary-50 text-primary-700 text-xs font-medium px-3 py-1.5 rounded-full mb-5">
              <Ambulance size={13} /> Emergency response in under 12 minutes, average
            </span>

            <h1 className="font-display text-5xl font-bold text-ink-900 leading-[1.1]">
              Your medicine cabinet, doctor, and ambulance — one tap away.
            </h1>

            <p className="text-ink-500 mt-5 text-lg max-w-lg">
              ArogyaMed connects patients, doctors, pharmacies, and emergency
              response into a single, verified healthcare platform.
            </p>

            {/* Search medicine */}
            <div className="mt-8 flex items-center gap-2 bg-white rounded-2xl p-2 shadow-soft max-w-lg">
              <div className="flex items-center gap-2 flex-1 px-3">
                <Search size={18} className="text-ink-300" />
                <input
                  placeholder="Search medicine, e.g. Paracetamol"
                  className="w-full py-2.5 outline-none text-sm placeholder:text-ink-300"
                />
              </div>
              <Link to="/register" className="btn-primary text-sm shrink-0">
                Search
              </Link>
            </div>

            <div className="mt-4 flex items-center gap-1.5 text-sm text-ink-500">
              <MapPin size={14} />
              <span>Showing pharmacies near your location</span>
            </div>
          </motion.div>
        </div>
      </section>

      {/* Categories */}
      <section className="max-w-7xl mx-auto px-6 py-14">
        <h2 className="font-display text-2xl font-bold text-ink-900 mb-6">
          Shop by category
        </h2>
        <div className="grid grid-cols-3 md:grid-cols-6 gap-4">
          {categories.map((cat, i) => (
            <motion.div
              key={cat.label}
              initial={{ opacity: 0, y: 12 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.35, delay: i * 0.05 }}
            >
              <Card hover className="flex flex-col items-center gap-2.5 py-6 cursor-pointer">
                <div className="w-11 h-11 rounded-xl bg-primary-50 text-primary-600 flex items-center justify-center">
                  <cat.icon size={20} />
                </div>
                <span className="text-xs font-medium text-ink-700 text-center">
                  {cat.label}
                </span>
              </Card>
            </motion.div>
          ))}
        </div>
      </section>

      {/* Offer banner */}
      <section className="max-w-7xl mx-auto px-6 pb-14">
        <div className="rounded-3xl bg-gradient-primary p-10 relative overflow-hidden">
          <div className="absolute -right-10 -top-10 w-56 h-56 bg-white/10 rounded-full blur-3xl" />
          <div className="relative z-10 flex flex-col md:flex-row md:items-center justify-between gap-6">
            <div>
              <h3 className="font-display text-2xl font-bold text-white">
                Flat 20% off on your first medicine order
              </h3>
              <p className="text-white/70 mt-1.5 text-sm">
                Verified medicines, delivered from licensed pharmacies near you.
              </p>
            </div>
            <Link
              to="/register"
              className="bg-white text-primary-700 font-medium rounded-xl px-6 py-3 flex items-center gap-2 w-fit hover:-translate-y-0.5 transition-transform"
            >
              Claim offer <ArrowRight size={16} />
            </Link>
          </div>
        </div>
      </section>

      {/* Top doctors */}
      <section className="max-w-7xl mx-auto px-6 py-14">
        <div className="flex items-center justify-between mb-6">
          <h2 className="font-display text-2xl font-bold text-ink-900">Top doctors</h2>
          <Link to="/register" className="text-primary-600 text-sm font-medium flex items-center gap-1">
            View all <ArrowRight size={14} />
          </Link>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-5">
          {doctors.map((doc, i) => (
            <motion.div
              key={doc.name}
              initial={{ opacity: 0, y: 12 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.35, delay: i * 0.06 }}
            >
              <Card hover>
                <div className="w-12 h-12 rounded-2xl bg-secondary-50 text-secondary-600 flex items-center justify-center mb-4">
                  <Stethoscope size={20} />
                </div>
                <p className="font-display font-semibold text-ink-900">{doc.name}</p>
                <p className="text-sm text-ink-500">{doc.spec} · {doc.exp}</p>
                <div className="flex items-center gap-1 mt-3 text-sm">
                  <Star size={14} className="fill-warning-500 text-warning-500" />
                  <span className="font-medium text-ink-900">{doc.rating}</span>
                </div>
              </Card>
            </motion.div>
          ))}
        </div>
      </section>

      {/* Ambulance booking CTA */}
      <section className="max-w-7xl mx-auto px-6 py-14">
        <Card className="grid md:grid-cols-2 gap-8 items-center !p-10">
          <div>
            <span className="inline-flex items-center gap-1.5 bg-accent-50 text-accent-600 text-xs font-medium px-3 py-1.5 rounded-full mb-4">
              <Ambulance size={13} /> Emergency Response
            </span>
            <h3 className="font-display text-2xl font-bold text-ink-900">
              Book an ambulance with live tracking
            </h3>
            <p className="text-ink-500 mt-2 text-sm">
              Real-time driver location, ETA, and hospital routing — from
              request to arrival.
            </p>
            <Link to="/ambulance" className="btn-primary inline-flex mt-5">
              Book now
            </Link>
          </div>
          <div className="h-48 rounded-2xl bg-gradient-mesh bg-surface flex items-center justify-center">
            <Ambulance size={56} className="text-primary-300" />
          </div>
        </Card>
      </section>

      {/* Health articles */}
      <section className="max-w-7xl mx-auto px-6 py-14">
        <h2 className="font-display text-2xl font-bold text-ink-900 mb-6">
          Health articles
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
          {articles.map((a, i) => (
            <motion.div
              key={a.title}
              initial={{ opacity: 0, y: 12 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.35, delay: i * 0.08 }}
            >
              <Card hover className="cursor-pointer h-full">
                <span className="text-xs font-medium text-primary-600">{a.tag}</span>
                <p className="font-display font-semibold text-ink-900 mt-2">
                  {a.title}
                </p>
              </Card>
            </motion.div>
          ))}
        </div>
      </section>

      {/* Testimonials */}
      <section className="max-w-7xl mx-auto px-6 py-14">
        <h2 className="font-display text-2xl font-bold text-ink-900 mb-6">
          What people are saying
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
          {testimonials.map((t, i) => (
            <motion.div
              key={t.name}
              initial={{ opacity: 0, y: 12 }}
              whileInView={{ opacity: 1, y: 0 }}
              viewport={{ once: true }}
              transition={{ duration: 0.35, delay: i * 0.08 }}
            >
              <Card className="h-full flex flex-col justify-between">
                <p className="text-sm text-ink-700 leading-relaxed">"{t.quote}"</p>
                <div className="mt-5">
                  <p className="text-sm font-medium text-ink-900">{t.name}</p>
                  <p className="text-xs text-ink-500">{t.role}</p>
                </div>
              </Card>
            </motion.div>
          ))}
        </div>
      </section>

      <Footer />
      <FloatingSOSButton />
    </div>
  );
}
