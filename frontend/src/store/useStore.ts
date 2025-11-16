import { create } from 'zustand';
import { Candidate, JobPosition, User } from '../types';

interface AppState {
  // User state
  user: User | null;
  isAuthenticated: boolean;
  setUser: (user: User | null) => void;
  setAuthenticated: (authenticated: boolean) => void;

  // Candidates state
  candidates: Candidate[];
  selectedCandidate: Candidate | null;
  setCandidates: (candidates: Candidate[]) => void;
  setSelectedCandidate: (candidate: Candidate | null) => void;
  addCandidate: (candidate: Candidate) => void;
  updateCandidate: (id: number, candidate: Partial<Candidate>) => void;
  deleteCandidate: (id: number) => void;

  // Jobs state
  jobs: JobPosition[];
  selectedJob: JobPosition | null;
  setJobs: (jobs: JobPosition[]) => void;
  setSelectedJob: (job: JobPosition | null) => void;
  addJob: (job: JobPosition) => void;
  updateJob: (id: number, job: Partial<JobPosition>) => void;
  deleteJob: (id: number) => void;

  // Filter state
  searchQuery: string;
  setSearchQuery: (query: string) => void;
}

export const useStore = create<AppState>((set) => ({
  // User state
  user: null,
  isAuthenticated: false,
  setUser: (user) => set({ user }),
  setAuthenticated: (authenticated) => set({ isAuthenticated: authenticated }),

  // Candidates state
  candidates: [],
  selectedCandidate: null,
  setCandidates: (candidates) => set({ candidates }),
  setSelectedCandidate: (candidate) => set({ selectedCandidate: candidate }),
  addCandidate: (candidate) => set((state) => ({
    candidates: [...state.candidates, candidate],
  })),
  updateCandidate: (id, candidateUpdate) => set((state) => ({
    candidates: state.candidates.map((c) =>
      c.id === id ? { ...c, ...candidateUpdate } : c
    ),
  })),
  deleteCandidate: (id) => set((state) => ({
    candidates: state.candidates.filter((c) => c.id !== id),
  })),

  // Jobs state
  jobs: [],
  selectedJob: null,
  setJobs: (jobs) => set({ jobs }),
  setSelectedJob: (job) => set({ selectedJob: job }),
  addJob: (job) => set((state) => ({
    jobs: [...state.jobs, job],
  })),
  updateJob: (id, jobUpdate) => set((state) => ({
    jobs: state.jobs.map((j) =>
      j.id === id ? { ...j, ...jobUpdate } : j
    ),
  })),
  deleteJob: (id) => set((state) => ({
    jobs: state.jobs.filter((j) => j.id !== id),
  })),

  // Filter state
  searchQuery: '',
  setSearchQuery: (query) => set({ searchQuery: query }),
}));
