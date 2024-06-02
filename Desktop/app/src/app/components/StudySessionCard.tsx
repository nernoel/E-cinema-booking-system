import React from 'react';
import {auth} from '@/app/utils/auth'
import { DateTime } from 'next-auth/providers/kakao';

interface CardProps {
  title: string;
  description: string;
  userEmail: string; // CHANGE TO AUTHOR FIRST NAME LATER
  location: string;
  isActive: boolean;
  isPrivate: boolean;
  createdAt:DateTime;
}



const StudySessionCard: React.FC<CardProps> = async ({title, description, userEmail, location, createdAt, isActive, isPrivate }) => {
  const session = await auth();
  return (
    <div className="max-w-md overflow-hidden rounded-lg bg-white shadow">
      <img src={session?.user?.image} className="h-14 w-14 rounded-full" alt="optional image" />
      <div className="p-4">
        <p className="mb-1 text-sm text-primary-500 text-gray-900">Posted by: {session?.user?.name!} <time>{createdAt}</time></p>
        <h3 className="text-xl font-medium text-gray-900">{title}</h3>
        <p className="mt-1 text-gray-500">{description}</p>
        <div className="mt-4 flex gap-2">
          
          <span className="inline-flex items-center gap-1 rounded-full bg-green-50 px-2 py-1 text-xs font-semibold text-green-600">Session type: {isPrivate ? true && "private study" : "open study"}</span>
          <span className="inline-flex items-center gap-1 rounded-full bg-indigo-50 px-2 py-1 text-xs font-semibold text-indigo-600">Study Location:{location}</span>
          <span className="inline-flex items-center gap-1 rounded-full bg-orange-50 px-2 py-1 text-xs font-semibold text-orange-600">Study Subject: </span>
          <span className="inline-flex items-center gap-1 rounded-full bg-green-600 px-2 py-1 text-xs font-semibold text-gray-100">JOIN</span>
        </div>
      </div>
    </div>
  );
}

export default StudySessionCard;
