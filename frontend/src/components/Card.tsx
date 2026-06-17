import React from 'react';

interface CardProps {
  title: string;
  children: React.ReactNode;
  icon?: React.ReactNode;
}

const Card: React.FC<CardProps> = ({ title, children, icon }) => {
  return (
    <div className="bg-white rounded-lg shadow-md p-6 border border-gray-200">
      <div className="flex items-center mb-4">
        {icon && <span className="mr-2 text-orange-600">{icon}</span>}
        <h2 className="text-xl font-semibold text-gray-800">{title}</h2>
      </div>
      <div>{children}</div>
    </div>
  );
};

export default Card;
