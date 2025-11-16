import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useStore } from '../store/useStore';
import { jobApi } from '../services/api';
import { JobPosition } from '../types';
import { Search, Plus, MapPin, DollarSign, Users } from 'lucide-react';

const JobsList: React.FC = () => {
  const { jobs, setJobs, searchQuery, setSearchQuery } = useStore();
  const [loading, setLoading] = useState(true);
  const [filterStatus, setFilterStatus] = useState('OPEN');

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        setLoading(true);
        const response = await jobApi.getAll();
        setJobs(response.data);
      } catch (error) {
        console.error('Error fetching jobs:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchJobs();
  }, [setJobs]);

  const filteredJobs = jobs.filter((job) =>
    job.title.toLowerCase().includes(searchQuery.toLowerCase()) &&
    (filterStatus === 'ALL' || job.status === filterStatus)
  );

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
        <div className="flex justify-between items-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900">Job Positions</h1>
          <button className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            <Plus size={20} className="mr-2" />
            Add Job Position
          </button>
        </div>

        {/* Search and Filter */}
        <div className="bg-white rounded-lg shadow p-4 mb-8">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="flex items-center">
              <Search className="text-gray-400 mr-2" />
              <input
                type="text"
                placeholder="Search jobs by title..."
                className="w-full outline-none"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </div>
            <div>
              <select
                value={filterStatus}
                onChange={(e) => setFilterStatus(e.target.value)}
                className="w-full px-4 py-2 border rounded-lg outline-none"
              >
                <option value="ALL">All Statuses</option>
                <option value="OPEN">Open</option>
                <option value="CLOSED">Closed</option>
                <option value="ON_HOLD">On Hold</option>
              </select>
            </div>
          </div>
        </div>

        {/* Jobs List */}
        <div className="space-y-4">
          {filteredJobs.map((job) => (
            <Link key={job.id} to={`/jobs/${job.id}`}>
              <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer">
                <div className="flex justify-between items-start mb-4">
                  <div>
                    <h3 className="text-2xl font-bold text-gray-900">{job.title}</h3>
                    <p className="text-gray-600">{job.department}</p>
                  </div>
                  <span className={`px-4 py-2 rounded-lg font-semibold text-white ${
                    job.status === 'OPEN'
                      ? 'bg-green-500'
                      : job.status === 'CLOSED'
                      ? 'bg-red-500'
                      : 'bg-yellow-500'
                  }`}>
                    {job.status}
                  </span>
                </div>

                <p className="text-gray-700 mb-4 line-clamp-2">{job.description}</p>

                <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-4">
                  <div className="flex items-center text-gray-600">
                    <MapPin size={18} className="mr-2" />
                    <span className="text-sm">{job.location}</span>
                  </div>

                  {job.salaryMin && job.salaryMax && (
                    <div className="flex items-center text-gray-600">
                      <DollarSign size={18} className="mr-2" />
                      <span className="text-sm">
                        ${job.salaryMin.toLocaleString()} - ${job.salaryMax.toLocaleString()}
                      </span>
                    </div>
                  )}

                  <div className="flex items-center text-gray-600">
                    <Users size={18} className="mr-2" />
                    <span className="text-sm">{job.applicationCount || 0} applications</span>
                  </div>
                </div>

                {job.requiredSkills && job.requiredSkills.length > 0 && (
                  <div>
                    <p className="text-sm font-semibold text-gray-600 mb-2">Required Skills</p>
                    <div className="flex flex-wrap gap-2">
                      {job.requiredSkills.slice(0, 5).map((skill) => (
                        <span
                          key={skill.id}
                          className="text-xs bg-blue-100 text-blue-800 px-3 py-1 rounded-full"
                        >
                          {skill.name}
                        </span>
                      ))}
                      {job.requiredSkills.length > 5 && (
                        <span className="text-xs bg-blue-100 text-blue-800 px-3 py-1 rounded-full">
                          +{job.requiredSkills.length - 5}
                        </span>
                      )}
                    </div>
                  </div>
                )}
              </div>
            </Link>
          ))}
        </div>

        {filteredJobs.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-600 text-lg">No job positions found</p>
          </div>
        )}
      </div>
    </main>
  );
};

export default JobsList;
