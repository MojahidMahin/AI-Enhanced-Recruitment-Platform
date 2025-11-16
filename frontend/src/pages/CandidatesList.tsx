import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useStore } from '../store/useStore';
import { candidateApi } from '../services/api';
import { Candidate } from '../types';
import { Search, Plus } from 'lucide-react';

const CandidatesList: React.FC = () => {
  const { candidates, setCandidates, searchQuery, setSearchQuery } = useStore();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCandidates = async () => {
      try {
        setLoading(true);
        const response = await candidateApi.getAll();
        setCandidates(response.data);
      } catch (error) {
        console.error('Error fetching candidates:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCandidates();
  }, [setCandidates]);

  const filteredCandidates = candidates.filter((candidate) =>
    `${candidate.firstName} ${candidate.lastName}`
      .toLowerCase()
      .includes(searchQuery.toLowerCase())
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
          <h1 className="text-3xl font-bold text-gray-900">Candidates</h1>
          <button className="flex items-center bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            <Plus size={20} className="mr-2" />
            Add Candidate
          </button>
        </div>

        {/* Search */}
        <div className="bg-white rounded-lg shadow p-4 mb-8">
          <div className="flex items-center">
            <Search className="text-gray-400 mr-2" />
            <input
              type="text"
              placeholder="Search candidates by name..."
              className="w-full outline-none"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
        </div>

        {/* Candidates Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredCandidates.map((candidate) => (
            <Link key={candidate.id} to={`/candidates/${candidate.id}`}>
              <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition cursor-pointer">
                <h3 className="text-xl font-bold text-gray-900">
                  {candidate.firstName} {candidate.lastName}
                </h3>
                <p className="text-gray-600 text-sm">{candidate.email}</p>
                {candidate.phoneNumber && (
                  <p className="text-gray-600 text-sm">{candidate.phoneNumber}</p>
                )}

                <div className="mt-4 space-y-3">
                  {candidate.matchingScore !== undefined && (
                    <div>
                      <p className="text-xs text-gray-500 font-semibold">Match Score</p>
                      <div className="w-full bg-gray-200 rounded-full h-2">
                        <div
                          className="bg-green-500 h-2 rounded-full"
                          style={{
                            width: `${((candidate.matchingScore || 0) * 100).toFixed(0)}%`,
                          }}
                        ></div>
                      </div>
                      <p className="text-xs text-gray-600 mt-1">
                        {((candidate.matchingScore || 0) * 100).toFixed(0)}%
                      </p>
                    </div>
                  )}

                  {candidate.sentimentScore !== undefined && (
                    <div>
                      <p className="text-xs text-gray-500 font-semibold">Sentiment</p>
                      <p className="text-sm text-gray-600">
                        {(candidate.sentimentScore * 100).toFixed(0)}%
                      </p>
                    </div>
                  )}

                  <div>
                    <p className="text-xs text-gray-500 font-semibold">Status</p>
                    <span className="inline-block px-3 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800">
                      {candidate.status}
                    </span>
                  </div>

                  {candidate.skills && candidate.skills.length > 0 && (
                    <div>
                      <p className="text-xs text-gray-500 font-semibold">Skills</p>
                      <div className="flex flex-wrap gap-2 mt-1">
                        {candidate.skills.slice(0, 3).map((skill) => (
                          <span key={skill.id} className="text-xs bg-gray-100 text-gray-700 px-2 py-1 rounded">
                            {skill.name}
                          </span>
                        ))}
                        {candidate.skills.length > 3 && (
                          <span className="text-xs bg-gray-100 text-gray-700 px-2 py-1 rounded">
                            +{candidate.skills.length - 3}
                          </span>
                        )}
                      </div>
                    </div>
                  )}
                </div>
              </div>
            </Link>
          ))}
        </div>

        {filteredCandidates.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-600 text-lg">No candidates found</p>
          </div>
        )}
      </div>
    </main>
  );
};

export default CandidatesList;
