import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { candidateApi } from '../services/api';
import { Candidate } from '../types';
import { ArrowLeft, Mail, Phone, Calendar } from 'lucide-react';

const CandidateDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [candidate, setCandidate] = useState<Candidate | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCandidate = async () => {
      try {
        setLoading(true);
        if (id) {
          const response = await candidateApi.getById(Number(id));
          setCandidate(response.data);
        }
      } catch (error) {
        console.error('Error fetching candidate:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCandidate();
  }, [id]);

  if (loading) {
    return (
      <div className="flex items-center justify-center h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (!candidate) {
    return (
      <main className="min-h-screen bg-gray-100 p-8">
        <div className="max-w-7xl mx-auto">
          <p className="text-gray-600">Candidate not found</p>
        </div>
      </main>
    );
  }

  return (
    <main className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto">
        <button
          onClick={() => navigate('/candidates')}
          className="flex items-center text-blue-600 hover:text-blue-800 mb-6"
        >
          <ArrowLeft size={20} className="mr-2" />
          Back to Candidates
        </button>

        <div className="bg-white rounded-lg shadow p-8">
          {/* Header */}
          <div className="mb-8 border-b pb-6">
            <h1 className="text-3xl font-bold text-gray-900 mb-2">
              {candidate.firstName} {candidate.lastName}
            </h1>
            <div className="flex flex-wrap gap-4 text-gray-600">
              <div className="flex items-center">
                <Mail size={18} className="mr-2" />
                <a href={`mailto:${candidate.email}`}>{candidate.email}</a>
              </div>
              {candidate.phoneNumber && (
                <div className="flex items-center">
                  <Phone size={18} className="mr-2" />
                  <a href={`tel:${candidate.phoneNumber}`}>{candidate.phoneNumber}</a>
                </div>
              )}
              <div className="flex items-center">
                <Calendar size={18} className="mr-2" />
                <span>Applied: {new Date(candidate.createdAt).toLocaleDateString()}</span>
              </div>
            </div>
          </div>

          {/* Summary */}
          {candidate.summary && (
            <div className="mb-8">
              <h2 className="text-xl font-bold text-gray-900 mb-4">Summary</h2>
              <p className="text-gray-700">{candidate.summary}</p>
            </div>
          )}

          {/* Scores */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
            {candidate.matchingScore !== undefined && (
              <div className="bg-blue-50 rounded-lg p-6">
                <h3 className="font-semibold text-gray-900 mb-2">Matching Score</h3>
                <div className="flex items-center">
                  <div className="flex-1">
                    <div className="w-full bg-gray-300 rounded-full h-3">
                      <div
                        className="bg-blue-600 h-3 rounded-full"
                        style={{
                          width: `${((candidate.matchingScore || 0) * 100).toFixed(0)}%`,
                        }}
                      ></div>
                    </div>
                  </div>
                  <span className="ml-4 text-2xl font-bold text-blue-600">
                    {((candidate.matchingScore || 0) * 100).toFixed(0)}%
                  </span>
                </div>
              </div>
            )}

            {candidate.sentimentScore !== undefined && (
              <div className="bg-green-50 rounded-lg p-6">
                <h3 className="font-semibold text-gray-900 mb-2">Sentiment Score</h3>
                <div className="flex items-center">
                  <div className="flex-1">
                    <div className="w-full bg-gray-300 rounded-full h-3">
                      <div
                        className="bg-green-600 h-3 rounded-full"
                        style={{
                          width: `${(((candidate.sentimentScore || 0) + 1) / 2) * 100}%`,
                        }}
                      ></div>
                    </div>
                  </div>
                  <span className="ml-4 text-2xl font-bold text-green-600">
                    {(candidate.sentimentScore || 0).toFixed(2)}
                  </span>
                </div>
              </div>
            )}
          </div>

          {/* Status */}
          <div className="mb-8">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Status</h2>
            <span className="inline-block px-4 py-2 text-lg font-semibold rounded-lg bg-blue-100 text-blue-800">
              {candidate.status}
            </span>
          </div>

          {/* Skills */}
          {candidate.skills && candidate.skills.length > 0 && (
            <div>
              <h2 className="text-xl font-bold text-gray-900 mb-4">Skills</h2>
              <div className="flex flex-wrap gap-3">
                {candidate.skills.map((skill) => (
                  <div key={skill.id} className="bg-gray-100 rounded-lg p-3">
                    <p className="font-semibold text-gray-900">{skill.name}</p>
                    {skill.category && (
                      <p className="text-sm text-gray-600">{skill.category}</p>
                    )}
                    {skill.proficiencyLevel && (
                      <p className="text-sm text-gray-600">
                        Level: {skill.proficiencyLevel}/5
                      </p>
                    )}
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </main>
  );
};

export default CandidateDetail;
