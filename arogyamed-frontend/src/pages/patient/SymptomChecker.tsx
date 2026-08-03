import { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { motion, AnimatePresence } from "framer-motion";
import { toast } from "sonner";
import {
  Sparkles,
  Stethoscope,
  AlertTriangle,
  Info,
  Star,
  IndianRupee,
  Calendar,
} from "lucide-react";
import { DashboardLayout } from "@/components/layout/DashboardLayout";
import { Card } from "@/components/common/Card";
import { Button } from "@/components/common/Button";
import { Badge } from "@/components/common/Badge";
import { aiService, type SymptomCheckResponse, type UrgencyLevel } from "@/services/aiService";

const urgencyStyles: Record<UrgencyLevel, { tone: "success" | "warning" | "accent"; label: string }> = {
  LOW: { tone: "success", label: "Low urgency" },
  MEDIUM: { tone: "warning", label: "Medium urgency" },
  HIGH: { tone: "warning", label: "High urgency" },
  EMERGENCY: { tone: "accent", label: "Emergency — seek help now" },
};

export default function SymptomChecker() {
  const [symptoms, setSymptoms] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [result, setResult] = useState<SymptomCheckResponse | null>(null);

  const mutation = useMutation({
    mutationFn: aiService.checkSymptoms,
    onSuccess: (data) => {
      setResult(data);
    },
    onError: () => {
      toast.error("Couldn't analyze symptoms right now. Please try again.");
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!symptoms.trim()) {
      toast.error("Please describe your symptoms first");
      return;
    }

    mutation.mutate({
      symptoms,
      age: age ? Number(age) : undefined,
      gender: gender || undefined,
    });
  };

  return (
    <DashboardLayout title="AI Symptom Checker">
      <div className="grid grid-cols-1 lg:grid-cols-5 gap-5">
        {/* Input panel */}
        <Card className="lg:col-span-2 h-fit">
          <div className="w-11 h-11 rounded-2xl bg-gradient-primary flex items-center justify-center text-white mb-4">
            <Sparkles size={20} />
          </div>

          <p className="font-display font-bold text-lg text-ink-900">
            Describe how you're feeling
          </p>
          <p className="text-sm text-ink-500 mt-1 mb-5">
            We'll suggest the right specialist and matching doctors on ArogyaMed.
          </p>

          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            <textarea
              value={symptoms}
              onChange={(e) => setSymptoms(e.target.value)}
              placeholder="e.g. I've had a headache and mild fever since yesterday..."
              rows={5}
              className="w-full rounded-xl border border-surface-border bg-white px-4 py-3 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100 resize-none"
            />

            <div className="grid grid-cols-2 gap-3">
              <input
                type="number"
                value={age}
                onChange={(e) => setAge(e.target.value)}
                placeholder="Age"
                className="rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              />
              <select
                value={gender}
                onChange={(e) => setGender(e.target.value)}
                className="rounded-xl border border-surface-border bg-white px-4 py-2.5 text-sm outline-none focus:border-primary-400 focus:ring-2 focus:ring-primary-100"
              >
                <option value="">Gender</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>
            </div>

            <Button type="submit" isLoading={mutation.isPending} className="w-full">
              Analyze symptoms
            </Button>
          </form>

          <div className="flex items-start gap-2 mt-5 text-xs text-ink-500 bg-surface rounded-xl p-3">
            <Info size={14} className="shrink-0 mt-0.5" />
            <span>
              This tool gives a suggestion, not a diagnosis. Always consult a
              doctor for medical advice.
            </span>
          </div>
        </Card>

        {/* Result panel */}
        <div className="lg:col-span-3">
          <AnimatePresence mode="wait">
            {!result && !mutation.isPending && (
              <Card className="h-full flex flex-col items-center justify-center py-20 text-center">
                <Stethoscope size={40} className="text-primary-200 mb-4" />
                <p className="font-display font-semibold text-ink-900">
                  Your results will appear here
                </p>
                <p className="text-sm text-ink-500 mt-1 max-w-xs">
                  Describe your symptoms on the left to get started.
                </p>
              </Card>
            )}

            {mutation.isPending && (
              <Card className="h-full flex flex-col items-center justify-center py-20">
                <div className="w-10 h-10 border-2 border-primary-200 border-t-primary-600 rounded-full animate-spin mb-4" />
                <p className="text-sm text-ink-500">Analyzing your symptoms...</p>
              </Card>
            )}

            {result && !mutation.isPending && (
              <motion.div
                key="result"
                initial={{ opacity: 0, y: 12 }}
                animate={{ opacity: 1, y: 0 }}
                className="flex flex-col gap-5"
              >
                <Card>
                  <div className="flex items-center justify-between mb-4">
                    <p className="font-display font-semibold text-ink-900">Assessment</p>
                    <Badge tone={urgencyStyles[result.urgencyLevel].tone}>
                      {result.urgencyLevel === "EMERGENCY" && (
                        <AlertTriangle size={12} className="inline mr-1" />
                      )}
                      {urgencyStyles[result.urgencyLevel].label}
                    </Badge>
                  </div>

                  <div className="flex flex-col gap-2 mb-4">
                    {result.possibleConditions.map((c, i) => (
                      <div key={i} className="flex items-center gap-2 text-sm text-ink-700">
                        <span className="w-1.5 h-1.5 rounded-full bg-primary-500 shrink-0" />
                        {c}
                      </div>
                    ))}
                  </div>

                  <div className="bg-primary-50 rounded-xl p-4 flex items-center justify-between">
                    <div>
                      <p className="text-xs text-primary-600 font-medium">Recommended specialist</p>
                      <p className="font-display font-bold text-primary-800">
                        {result.recommendedSpecialization}
                      </p>
                    </div>
                    <Stethoscope size={24} className="text-primary-400" />
                  </div>

                  {!result.aiGenerated && (
                    <p className="text-xs text-ink-300 mt-3">
                      Generated using rule-based triage
                    </p>
                  )}
                </Card>

                <div>
                  <p className="font-display font-semibold text-ink-900 mb-3">
                    Matching doctors ({result.recommendedDoctors.length})
                  </p>

                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    {result.recommendedDoctors.map((doc) => (
                      <Card key={doc.id} hover>
                        <div className="flex items-center gap-3">
                          <div className="w-10 h-10 rounded-xl bg-secondary-50 text-secondary-600 flex items-center justify-center shrink-0">
                            <Stethoscope size={18} />
                          </div>
                          <div className="min-w-0">
                            <p className="font-medium text-ink-900 truncate">{doc.fullName}</p>
                            <p className="text-xs text-ink-500 truncate">{doc.hospitalName}</p>
                          </div>
                        </div>

                        <div className="flex items-center justify-between mt-4 text-xs text-ink-500">
                          <span className="flex items-center gap-1">
                            <Star size={12} className="fill-warning-500 text-warning-500" />
                            {doc.experienceYears} yrs exp
                          </span>
                          <span className="flex items-center gap-1 font-medium text-ink-900">
                            <IndianRupee size={12} />
                            {doc.consultationFee}
                          </span>
                        </div>

                        <Button size="sm" variant="secondary" className="w-full mt-3">
                          <Calendar size={14} /> Book appointment
                        </Button>
                      </Card>
                    ))}

                    {result.recommendedDoctors.length === 0 && (
                      <p className="text-sm text-ink-300 col-span-2 text-center py-6">
                        No matching doctors found in this specialization yet.
                      </p>
                    )}
                  </div>
                </div>

                <p className="text-xs text-ink-300 text-center px-4">{result.disclaimer}</p>
              </motion.div>
            )}
          </AnimatePresence>
        </div>
      </div>
    </DashboardLayout>
  );
}
