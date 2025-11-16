import React, { useEffect, useState } from 'react';
import { useStore } from '../store/useStore';
import { candidateApi, jobApi } from '../services/api';
import { Candidate, JobPosition } from '../types';
import { TrendingUp, Users, Briefcase } from 'lucide-react';

const Dashboard: React.FC = () => {
  const { candidates, jobs, setCandidates, setJobs } = useStore();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [candidatesRes, jobsRes] = await Promise.all([
          candidateApi.getAll(),
          jobApi.getAll(),
        ]);
        setCandidates(candidatesRes.data);
        setJobs(jobsRes.data);
      } catch (error) {
        console.error('Error fetching dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [setCandidates, setJobs]);

  const topCandidates = candidates
    .sort((a, b) => (b.matchingScore || 0) - (a.matchingScore || 0))
    .slice(0, 5);

  const openJobs = jobs.filter((j) => j.status === 'OPEN');

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">Dashboard</h1>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Total Candidates</p>
                <p className="text-3xl font-bold text-gray-900">{candidates.length}</p>
              </div>
              <Users className="text-blue-600" size={32} />
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Open Positions</p>
                <p className="text-3xl font-bold text-gray-900">{openJobs.length}</p>
              </div>
              <Briefcase className="text-green-600" size={32} />
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-gray-500 text-sm">Avg Match Score</p>
                <p className="text-3xl font-bold text-gray-900">
                  {candidates.length > 0
                    ? (
                        candidates.reduce((sum, c) => sum + (c.matchingScore || 0), 0) /
                        candidates.length
                      ).toFixed(2)
                    : 'N/A'}
                </p>
              </div>
              <TrendingUp className="text-purple-600" size={32} />
            </div>
          </div>
        </div>

        {/* Top Candidates */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <h2 className="text-xl font-bold text-gray-900 mb-4">Top Candidates</h2>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                    Name
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                    Email
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                    Match Score
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">
                    Status
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200">
                {topCandidates.map((candidate) => (
                  <tr key={candidate.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap">
                      {candidate.firstName} {candidate.lastName}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">{candidate.email}</td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="w-full bg-gray-200 rounded-full h-2">
                        <div
                          className="bg-green-500 h-2 rounded-full"
                          style={{ width: `${((candidate.matchingScore || 0) * 100).toFixed(0)}%` }}
                        ></div>
                      </div>
                      {((candidate.matchingScore || 0) * 100).toFixed(0)}%
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className="px-2 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                        {candidate.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Open Positions */}
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-bold text-gray-900 mb-4">Open Positions</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {openJobs.slice(0, 6).map((job) => (
              <div key={job.id} className="border rounded-lg p-4 hover:shadow-lg transition">
                <h3 className="font-bold text-gray-900">{job.title}</h3>
                <p className="text-sm text-gray-600">{job.department}</p>
                <p className="text-sm text-gray-600">{job.location}</p>
                <p className="text-xs text-gray-500 mt-2">
                  {job.applicationCount || 0} applications
                </p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </main>
  );
};

export default Dashboard;
