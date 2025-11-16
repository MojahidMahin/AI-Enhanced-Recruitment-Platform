import axios, { AxiosInstance } from 'axios';
import { Candidate, JobPosition, Interview, Assessment, JobApplication } from '../types';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if it exists
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Candidate APIs
export const candidateApi = {
  getAll: () => api.get<Candidate[]>('/candidates'),
  getById: (id: number) => api.get<Candidate>(`/candidates/${id}`),
  getByEmail: (email: string) => api.get<Candidate>(`/candidates/email/${email}`),
  create: (candidate: Partial<Candidate>) => api.post<Candidate>('/candidates', candidate),
  update: (id: number, candidate: Partial<Candidate>) => api.put<Candidate>(`/candidates/${id}`, candidate),
  delete: (id: number) => api.delete(`/candidates/${id}`),
  search: (name: string) => api.get<Candidate[]>('/candidates/search', { params: { name } }),
  getTopCandidates: (limit: number = 10) => api.get<Candidate[]>('/candidates/top-candidates', { params: { limit } }),
  parseResume: (id: number, resumeText: string) => api.post<Candidate>(`/candidates/${id}/parse-resume`, { resumeText }),
};

// Job Position APIs
export const jobApi = {
  getAll: () => api.get<JobPosition[]>('/jobs'),
  getById: (id: number) => api.get<JobPosition>(`/jobs/${id}`),
  create: (job: Partial<JobPosition>) => api.post<JobPosition>('/jobs', job),
  update: (id: number, job: Partial<JobPosition>) => api.put<JobPosition>(`/jobs/${id}`, job),
  delete: (id: number) => api.delete(`/jobs/${id}`),
  getOpen: () => api.get<JobPosition[]>('/jobs/open'),
  searchByTitle: (title: string) => api.get<JobPosition[]>('/jobs/search/title', { params: { title } }),
  searchByDepartment: (department: string) => api.get<JobPosition[]>('/jobs/search/department', { params: { department } }),
  searchByLocation: (location: string) => api.get<JobPosition[]>('/jobs/search/location', { params: { location } }),
};

// Interview APIs
export const interviewApi = {
  getAll: () => api.get<Interview[]>('/interviews'),
  getById: (id: number) => api.get<Interview>(`/interviews/${id}`),
  getByCandidateId: (candidateId: number) => api.get<Interview[]>(`/interviews/candidate/${candidateId}`),
  getByJobId: (jobId: number) => api.get<Interview[]>(`/interviews/job/${jobId}`),
  create: (interview: Partial<Interview>) => api.post<Interview>('/interviews', interview),
  update: (id: number, interview: Partial<Interview>) => api.put<Interview>(`/interviews/${id}`, interview),
  delete: (id: number) => api.delete(`/interviews/${id}`),
  getUpcoming: () => api.get<Interview[]>('/interviews/upcoming'),
};

// Assessment APIs
export const assessmentApi = {
  getAll: () => api.get<Assessment[]>('/assessments'),
  getById: (id: number) => api.get<Assessment>(`/assessments/${id}`),
  getByCandidateId: (candidateId: number) => api.get<Assessment[]>(`/assessments/candidate/${candidateId}`),
  create: (assessment: Partial<Assessment>) => api.post<Assessment>('/assessments', assessment),
  update: (id: number, assessment: Partial<Assessment>) => api.put<Assessment>(`/assessments/${id}`, assessment),
  delete: (id: number) => api.delete(`/assessments/${id}`),
};

// Job Application APIs
export const applicationApi = {
  getAll: () => api.get<JobApplication[]>('/applications'),
  getById: (id: number) => api.get<JobApplication>(`/applications/${id}`),
  getByCandidateId: (candidateId: number) => api.get<JobApplication[]>(`/applications/candidate/${candidateId}`),
  getByJobId: (jobId: number) => api.get<JobApplication[]>(`/applications/job/${jobId}`),
  create: (application: Partial<JobApplication>) => api.post<JobApplication>('/applications', application),
  update: (id: number, application: Partial<JobApplication>) => api.put<JobApplication>(`/applications/${id}`, application),
  delete: (id: number) => api.delete(`/applications/${id}`),
};

export default api;
