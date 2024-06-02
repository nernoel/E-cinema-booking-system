import { auth } from "@/app/utils/auth";
import { GetServerSidePropsContext } from "next";

export default async function ProfileCard() {
    const session = await auth();

    return (
      <div className="flex items-center gap-4">
        <div className="relative">
        <img className="w-10 h-10 rounded-full" src={session?.user?.email} alt=""></img>
        <span className="bottom-0 left-7 absolute  w-3.5 h-3.5 bg-green-400 border-2 border-white dark:border-gray-800 rounded-full"></span>
      </div>
          <div className="font-medium dark:text-white">
            <div>{session?.user?.name}</div>
          <div className="text-sm text-gray-500 dark:text-gray-400">Senior</div>
          <div className="text-sm text-gray-500 dark:text-gray-400">CS major</div>
          <div className="text-sm text-gray-500 dark:text-gray-400">University of Earth</div>
      </div>
    </div>
    )


}



