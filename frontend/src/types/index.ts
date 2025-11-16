export interface Candidate {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber?: string;
  summary?: string;
  resumeFilePath?: string;
  sentimentScore?: number;
  matchingScore?: number;
  status: 'NEW' | 'UNDER_REVIEW' | 'SHORTLISTED' | 'REJECTED' | 'HIRED';
  skills: Skill[];
  createdAt: string;
  updatedAt: string;
}

export interface Skill {
  id: number;
  name: string;
  description?: string;
  category: 'PROGRAMMING' | 'DATABASE' | 'CLOUD' | 'TOOLS' | 'SOFT_SKILLS' | 'FRAMEWORKS' | 'METHODOLOGY';
  proficiencyLevel?: number;
}

export interface JobPosition {
  id: number;
  title: string;
  description: string;
  department: string;
  location: string;
  salaryMin?: number;
  salaryMax?: number;
  status: 'OPEN' | 'CLOSED' | 'ON_HOLD';
  requiredSkills: Skill[];
  applicationCount?: number;
  createdAt: string;
  updatedAt: string;
}

export interface Interview {
  id: number;
  candidateId: number;
  jobId: number;
  scheduledTime: string;
  completedTime?: string;
  status: 'SCHEDULED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED' | 'NO_SHOW';
  interviewer?: string;
  notes?: string;
  rating?: number;
}

export interface Assessment {
  id: number;
  candidateId: number;
  assessmentType: string;
  assessmentContent?: string;
  score?: number;
  sentimentScore?: number;
  feedback?: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';
  createdAt: string;
  updatedAt: string;
}

export interface JobApplication {
  id: number;
  candidateId: number;
  jobId: number;
  status: 'APPLIED' | 'SCREENING' | 'SHORTLISTED' | 'REJECTED' | 'OFFER_SENT' | 'ACCEPTED' | 'DECLINED';
  compatibilityScore?: number;
  reasonForRejection?: string;
  appliedAt: string;
  updatedAt: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: 'ADMIN' | 'RECRUITER' | 'HIRING_MANAGER' | 'CANDIDATE';
  enabled: boolean;
  createdAt: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}
