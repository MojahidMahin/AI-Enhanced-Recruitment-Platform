import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useStore } from '../store/useStore';
import { LogOut, Menu } from 'lucide-react';

const Navbar: React.FC = () => {
  const navigate = useNavigate();
  const { user, setUser, setAuthenticated } = useStore();
  const [isOpen, setIsOpen] = React.useState(false);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setUser(null);
    setAuthenticated(false);
    navigate('/login');
  };

  return (
    <nav className="bg-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center">
            <Link to="/" className="text-2xl font-bold text-blue-600">
              RecruitAI
            </Link>
            <div className="hidden md:ml-10 md:flex space-x-4">
              <Link
                to="/candidates"
                className="text-gray-600 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium"
              >
                Candidates
              </Link>
              <Link
                to="/jobs"
                className="text-gray-600 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium"
              >
                Jobs
              </Link>
            </div>
          </div>

          <div className="flex items-center">
            <span className="mr-4 text-sm text-gray-600">
              {user?.firstName} {user?.lastName}
            </span>
            <button
              onClick={handleLogout}
              className="flex items-center text-gray-600 hover:text-red-600"
            >
              <LogOut size={20} />
            </button>
            <button
              onClick={() => setIsOpen(!isOpen)}
              className="md:hidden ml-2 text-gray-600"
            >
              <Menu size={20} />
            </button>
          </div>
        </div>

        {isOpen && (
          <div className="md:hidden pb-4">
            <Link
              to="/candidates"
              className="block text-gray-600 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium"
            >
              Candidates
            </Link>
            <Link
              to="/jobs"
              className="block text-gray-600 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium"
            >
              Jobs
            </Link>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
