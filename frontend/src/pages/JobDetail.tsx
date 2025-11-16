import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { jobApi } from '../services/api';
import { JobPosition } from '../types';
import { ArrowLeft, MapPin, DollarSign, Users, Calendar } from 'lucide-react';

const JobDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [job, setJob] = useState<JobPosition | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchJob = async () => {
      try {
        setLoading(true);
        if (id) {
          const response = await jobApi.getById(Number(id));
          setJob(response.data);
        }
      } catch (error) {
        console.error('Error fetching job:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchJob();
  }, [id]);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (!job) {
    return (
      <main className="min-h-screen bg-gray-100 p-8">
        <div className="max-w-4xl mx-auto">
          <p className="text-gray-600">Job position not found</p>
        </div>
      </main>
    );
  }

  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto">
        <button
          onClick={() => navigate('/jobs')}
          className="flex items-center text-blue-600 hover:text-blue-800 mb-6"
        >
          <ArrowLeft size={20} className="mr-2" />
          Back to Jobs
        </button>

        <div className="bg-white rounded-lg shadow p-8">
          {/* Header */}
          <div className="mb-8 border-b pb-6">
            <div className="flex justify-between items-start mb-4">
              <div>
                <h1 className="text-3xl font-bold text-gray-900">{job.title}</h1>
                <p className="text-gray-600 text-lg mt-2">{job.department}</p>
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

            <div className="flex flex-wrap gap-4 text-gray-600 mt-4">
              <div className="flex items-center">
                <MapPin size={18} className="mr-2" />
                <span>{job.location}</span>
              </div>

              {job.salaryMin && job.salaryMax && (
                <div className="flex items-center">
                  <DollarSign size={18} className="mr-2" />
                  <span>
                    ${job.salaryMin.toLocaleString()} - ${job.salaryMax.toLocaleString()}
                  </span>
                </div>
              )}

              <div className="flex items-center">
                <Users size={18} className="mr-2" />
                <span>{job.applicationCount || 0} Applications</span>
              </div>

              <div className="flex items-center">
                <Calendar size={18} className="mr-2" />
                <span>Posted: {new Date(job.createdAt).toLocaleDateString()}</span>
              </div>
            </div>
          </div>

          {/* Description */}
          {job.description && (
            <div className="mb-8">
              <h2 className="text-xl font-bold text-gray-900 mb-4">Description</h2>
              <p className="text-gray-700 whitespace-pre-wrap">{job.description}</p>
            </div>
          )}

          {/* Required Skills */}
          {job.requiredSkills && job.requiredSkills.length > 0 && (
            <div className="mb-8">
              <h2 className="text-xl font-bold text-gray-900 mb-4">Required Skills</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                {job.requiredSkills.map((skill) => (
                  <div key={skill.id} className="bg-blue-50 rounded-lg p-4">
                    <p className="font-semibold text-gray-900">{skill.name}</p>
                    {skill.category && (
                      <p className="text-sm text-gray-600">{skill.category}</p>
                    )}
                    {skill.description && (
                      <p className="text-sm text-gray-700 mt-2">{skill.description}</p>
                    )}
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Action Buttons */}
          <div className="flex gap-4">
            <button className="flex-1 bg-blue-600 text-white px-6 py-3 rounded-lg font-semibold hover:bg-blue-700 transition">
              View Candidates
            </button>
            <button className="flex-1 bg-gray-200 text-gray-900 px-6 py-3 rounded-lg font-semibold hover:bg-gray-300 transition">
              Edit Position
            </button>
          </div>
        </div>
      </div>
    </main>
  );
};

export default JobDetail;
